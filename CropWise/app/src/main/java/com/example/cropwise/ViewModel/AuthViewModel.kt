package com.example.cropwise.ViewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import co.yml.charts.common.model.Point
import com.example.cropwise.model.Locationdata
import com.example.cropwise.model.SendPricePredict
import com.example.cropwise.model.cropParams
import com.example.cropwise.model.userModel
import com.example.cropwise.navigation.Route
import com.example.cropwise.utils.RetrofitInstance.api
import com.example.cropwise.utils.RetrofitInstance.api2
import com.example.cropwise.utils.RetrofitInstance.api3
import com.example.cropwise.utils.SharedPrefs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    private val userRef = db.getReference("users")

//    private val storageRef = Firebase.storage.reference

    private val _firebaseUser = MutableLiveData<FirebaseUser?>()
    val firebaseUser : MutableLiveData<FirebaseUser?> = _firebaseUser

    private val _error = MutableLiveData<String>()
//    val error : LiveData<String> = _error

    private val _cropRecommendation = MutableLiveData<String?>(null)
    val cropRecommendation : LiveData<String?> = _cropRecommendation

    private val _userFirstName = MutableLiveData<String?>(null)
    val userFirstName : LiveData<String?> = _userFirstName

    private val _location = mutableStateOf<Locationdata?>(null)
            val location: State<Locationdata?> = _location





    init {
        _firebaseUser.value =auth.currentUser
    }

    fun login(email: String, password: String){

        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _firebaseUser.postValue(auth.currentUser)
                }
                else {
                    _error.postValue("Something went wrong.")
                }
            }
    }

    fun register(email: String, password: String, firstName: String, lastName: String,context: Context){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _firebaseUser.postValue(auth.currentUser)
                    saveData(firstName,lastName,email,password,auth.currentUser?.uid, context)
                }
                else {
                    _error.postValue("Something went wrong.")
                }
            }
    }

    private fun saveData(firstName: String,lastName: String,email: String,password: String,uid: String?,context : Context) {
        val userData = userModel(firstName,lastName,email,password,uid!!)
        userRef.child(uid).setValue(userData)
            .addOnSuccessListener {
                SharedPrefs.storeData(firstName,lastName,email,context)
            }.addOnFailureListener {

            }
    }


    fun logout() {
        auth.signOut()
        _firebaseUser.postValue(null)
    }

    fun fetchusername() {
        val userId = auth.currentUser?.uid ?: return
        val database = FirebaseDatabase.getInstance().reference
        val userReference = database.child("users").child(userId).child("firstName")

        userReference.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val firstName = snapshot.value.toString()
                // Do something with the firstName (e.g., display it, store it, etc.)
                Log.d("FirebaseData", "First Name: $firstName")
                _userFirstName.postValue(firstName)
            } else {
                // Handle the case where the "firstName" field doesn't exist
                Log.d("FirebaseData", "First Name not found")
            }
        }.addOnFailureListener { exception ->
            // Handle database errors
            Log.e("FirebaseData", "Error fetching first name", exception)
        }
    }

    fun updateParams(Nitrogen: Int,Phosphorus:Int,Potassium:Int,Ph:Int){
        val userId = auth.currentUser?.uid ?: return
        val database = FirebaseDatabase.getInstance().reference
        val paramsRef = database.child("users").child(userId)

        val paramsData = mapOf(
            "Nitrogen" to Nitrogen,
            "Phosphorus" to Phosphorus,
            "Potassium" to Potassium,
            "Ph" to Ph
        )

        paramsRef.updateChildren(paramsData)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    paramsRef.updateChildren(paramsData)
                } else {
                    _error.postValue("Something went wrong.")
                }
            }
    }

    fun updateLocation(newLocation : Locationdata) {

        _location.value = newLocation
    }

    fun UpdateLocation(Latitude:Double,Longitude:Double){
        val userId = auth.currentUser?.uid ?: return
        val database = FirebaseDatabase.getInstance().reference
        val paramsRef = database.child("users").child(userId)

        val locationData = mapOf(
            "Latitude" to Latitude,
            "Longitude" to Longitude,
        )
        paramsRef.updateChildren(locationData)
    }

    fun UpdateExtraParams() {
        val userId = auth.currentUser?.uid ?: return
        val database = FirebaseDatabase.getInstance().reference
        val paramsRef = database.child("users").child(userId)
        val latitude = database.child("users").child(userId).child("Latitude")
        val longitude = database.child("users").child(userId).child("Longitude")
        latitude.get().addOnSuccessListener { latitudeSnapshot ->
            val Latitude = latitudeSnapshot.getValue(Double::class.java) ?: 0.0
            longitude.get().addOnSuccessListener { longitudeSnapshot ->
                val Longitude = longitudeSnapshot.getValue(Double::class.java) ?: 0.0

                viewModelScope.launch {
                    try {
                        val key = "6ea3cd63b1ba455da98132153241604"
                        Log.d("ExtraParams", Longitude.toString())
                        val loc = "$Latitude,$Longitude"
                        Log.d("ExtraParams", "ExtraParamsRequest triggered")
                        Log.d("ExtraParams", loc)
                        val response = api3.getExtraparams(key,loc)
                        if (response.isSuccessful && response.body() != null) {
                            val responseData = response.body()!!
                            Log.d("ExtraParams", "Raw response Received")
                            val region = responseData.location.region
                            val humidity = responseData.current.humidity
                            val temperature = responseData.current.temp_c
                            val rainfall = responseData.current.precip_mm
                            Log.d("ExtraParams", "responseData: $region")
                            Log.d("ExtraParams", "responseData: $humidity")
                            Log.d("ExtraParams", "responseData: $temperature")
                            Log.d("ExtraParams", "responseData: $rainfall")
                            val ExtraParamData = mapOf(
                                "State" to region,
                                "Humidity" to humidity,
                                "Temperature" to temperature,
                                "Rainfall" to rainfall,
                            )
                            paramsRef.updateChildren(ExtraParamData)
                            Log.d("ExtraParams", "ExtraParams Updated")
                            // Handle successful response
                        } else {
                            // Handle error
                        }
                    } catch (e: Exception) {
                        //  Handle network or other exceptions
                    }
                }

            }
        }
    }

    fun sendCropRequest(navController: NavHostController,context: Context) {
        val userId = auth.currentUser?.uid ?: return
        val database = FirebaseDatabase.getInstance().reference
        val nitrogen = database.child("users").child(userId).child("Nitrogen")
        val phosphorus = database.child("users").child(userId).child("Phosphorus")
        val potassium = database.child("users").child(userId).child("Potassium")
        val ph = database.child("users").child(userId).child("Ph")
        val rainfall = database.child("users").child(userId).child("Rainfall")
        val temp = database.child("users").child(userId).child("Temperature")
        val humid = database.child("users").child(userId).child("Humidity")

        nitrogen.get().addOnSuccessListener { nitrogenSnapshot ->
            val nitrogenValue = nitrogenSnapshot.getValue(Int::class.java) ?: 0
            if(nitrogenValue == 0){
                Toast.makeText(context,"This feature Requires Parameters Please Update Them ", Toast.LENGTH_LONG).show()
                navController.navigate(Route.ParamsUpdater.routes)
            }

            phosphorus.get().addOnSuccessListener { phosphorusSnapshot ->
                val phosphorusValue = phosphorusSnapshot.getValue(Int::class.java) ?: 0

                potassium.get().addOnSuccessListener { potassiumSnapshot ->
                    val potassiumValue = potassiumSnapshot.getValue(Int::class.java) ?: 0

                    ph.get().addOnSuccessListener { phSnapshot ->
                        val phValue = phSnapshot.getValue(Int::class.java) ?: 0

                        rainfall.get().addOnSuccessListener { rainSnapshot ->
                            val rainfallValue = rainSnapshot.getValue(Int::class.java) ?: 0

                            temp.get().addOnSuccessListener { tempSnapshot ->
                                val tempValue = tempSnapshot.getValue(Int::class.java) ?: 0

                                humid.get().addOnSuccessListener { humidSnapshot ->
                                    val humidValue = humidSnapshot.getValue(Int::class.java) ?: 0


                                    val requestData = cropParams(
                                        N = nitrogenValue,
                                        P = phosphorusValue,
                                        K = potassiumValue,
                                        ph = phValue,
                                        rainfall = rainfallValue,
                                        temperature = tempValue,
                                        humidity = humidValue
                                    )



                                    viewModelScope.launch {
                                        try {
                                            Log.d("CropRecom", "sendCropRequest triggered")
                                            Log.d("CropRecom", "requestDataN: ${requestData.N}")
                                            Log.d(
                                                "CropRecom",
                                                "requestDataK: ${requestData.rainfall}"
                                            )
                                            Log.d(
                                                "CropRecom",
                                                "requestDataph: ${requestData.humidity}"
                                            )
                                            Log.d(
                                                "CropRecom",
                                                "Current crop recommendation: ${_cropRecommendation.value}"
                                            )

                                            val response = api.getCropRecom(requestData)
                                            if (response.isSuccessful && response.body() != null) {
                                                val responseData = response.body()!!
                                                Log.d("CropRecom", "Raw response: $responseData")
                                                val predictedCrop =
                                                    responseData.Prediction.firstOrNull()
                                                Log.d("CropRecom", "Raw response: $predictedCrop")
                                                _cropRecommendation.postValue(predictedCrop)
                                                // Handle successful response
                                            } else {
                                                // Handle error
                                            }
                                        } catch (e: Exception) {
                                            //  Handle network or other exceptions
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    val pointsData0: MutableState<List<Point>> = mutableStateOf(emptyList())
    val pointsData1: MutableState<List<Point>> = mutableStateOf(emptyList())
    val pointsData2: MutableState<List<Point>> = mutableStateOf(emptyList())
    val pointsData3: MutableState<List<Point>> = mutableStateOf(emptyList())
    val loading: MutableState<Boolean> = mutableStateOf(false)
    fun priceRequest() {
        val userId = auth.currentUser?.uid ?: return
        val database = FirebaseDatabase.getInstance().reference
        val state = database.child("users").child(userId).child("State")
        state.get().addOnSuccessListener { StringSnapshot ->
            val StringState = StringSnapshot.getValue(String::class.java) ?: ""
            val State = mapStringToInteger(StringState)
            viewModelScope.launch {
                loading.value = true
                pointsData0.value = emptyList()
                pointsData1.value = emptyList()
                pointsData2.value = emptyList()
                pointsData3.value = emptyList()
                try {
                    val newData0 = mutableListOf<Point>()
                    val newData1 = mutableListOf<Point>()
                    val newData2 = mutableListOf<Point>()
                    val newData3 = mutableListOf<Point>()

//                    Commodity3

                    for (month in 1..12) {
                        val requestdata = SendPricePredict(state = State,commodity = 3,month = month)
                        Log.d("PricePredict", "Request data:$requestdata ")
                        val response = api2.getPricePredict(requestdata)
                        if (response.isSuccessful && response.body() != null) {
                            val responseData = response.body()!!
                            val x = month.toFloat()
                            val y = responseData.predicted_price.toFloat()
                            newData3.add(Point(x, y))
                            Log.d("PriceComposable",newData3.toString())
                            Log.d("loadingComposable",loading.value.toString())
                        }
                    }
                    pointsData3.value= newData3
                    Log.d("PriceComposable",pointsData3.value.toString())

                    //                    Commodity2

                    for (month in 1..12) {
                        val requestdata = SendPricePredict(state = State,commodity = 2,month = month)
                        Log.d("PricePredict", "Request data:$requestdata ")
                        val response = api2.getPricePredict(requestdata)
                        if (response.isSuccessful && response.body() != null) {
                            val responseData = response.body()!!
                            val x = month.toFloat()
                            val y = responseData.predicted_price.toFloat()
                            newData2.add(Point(x, y))
                            Log.d("PriceComposable",newData2.toString())
                            Log.d("loadingComposable",loading.value.toString())
                        }
                    }
                    pointsData2.value= newData2
                    Log.d("PriceComposable",pointsData2.value.toString())

                    //                    Commodity1

                    for (month in 1..12) {
                        val requestdata = SendPricePredict(state = State,commodity = 1,month = month)
                        Log.d("PricePredict", "Request data:$requestdata ")
                        val response = api2.getPricePredict(requestdata)
                        if (response.isSuccessful && response.body() != null) {
                            val responseData = response.body()!!
                            val x = month.toFloat()
                            val y = responseData.predicted_price.toFloat()
                            newData1.add(Point(x, y))
                            Log.d("PriceComposable",newData3.toString())
                            Log.d("loadingComposable",loading.value.toString())
                        }
                    }
                    pointsData1.value= newData1
                    Log.d("PriceComposable",pointsData1.value.toString())

                    //                    Commodity0

                    for (month in 1..12) {
                        val requestdata = SendPricePredict(state = State,commodity = 0,month = month)
                        Log.d("PricePredict", "Request data:$requestdata ")
                        val response = api2.getPricePredict(requestdata)
                        if (response.isSuccessful && response.body() != null) {
                            val responseData = response.body()!!
                            val x = month.toFloat()
                            val y = responseData.predicted_price.toFloat()
                            newData0.add(Point(x, y))
                            Log.d("PriceComposable",newData3.toString())
                            if(month == 12){
                                loading.value = false
                            }
                            Log.d("loadingComposable",loading.value.toString())
                        }
                    }
                    pointsData0.value= newData0
                    Log.d("PriceComposable",pointsData0.value.toString())
                }catch (e:Exception){
                }
            }
        }
    }

    private fun mapStringToInteger(stateString: String): Int {
        return when (stateString) {
            "Chhattisgarh" -> 0
            "Gujarat" -> 1
            "Haryana" -> 2
            "Karnataka" -> 3
            "Madhya Pradesh" -> 4
            "Maharashtra" -> 5
            "NCT of Delhi" -> 6
            "Punjab" -> 7
            "Rajasthan" -> 8
            "Uttar Pradesh" -> 9
            "Uttarakhand" -> 10
            "Bihar" -> 11
            "Andhra Pradesh" -> 12
            "Odisha" -> 13
            "Tamil Nadu" -> 14
            "Telangana" -> 15
            "Pondicherry" -> 16
            "Assam" -> 17
            "Jharkhand" -> 18
            "Manipur" -> 19
            "Nagaland" -> 20
            "Chandigarh" -> 21
            "Goa" -> 22
            "Himachal Pradesh" -> 23
            "Jammu and Kashmir" -> 24
            "Kerala" -> 25
            "Meghalaya" -> 26
            "Mizoram" -> 27
            "Tripura" -> 28
            "West Bengal" -> 29
            "Andaman and Nicobar" -> 30
            // ... add mappings for all 30 states ...
            else -> 0 // Or a more appropriate default if no mapping exists
        }
    }
}

