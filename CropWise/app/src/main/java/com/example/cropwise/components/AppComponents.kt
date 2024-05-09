package com.example.cropwise.components

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.rounded.MailOutline
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cropwise.MainActivity
import com.example.cropwise.R
import com.example.cropwise.ViewModel.AuthViewModel
import com.example.cropwise.navigation.Route
import com.example.cropwise.utils.LocationUtils
import com.google.firebase.auth.FirebaseUser

//Field Components

@Composable
fun TextValue(labelValue: String,textValue: String, onValueChange: (String) -> Unit,  isError: Boolean = false){
    var isFocused by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = textValue,
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .onFocusChanged { isFocused = it.isFocused },
        onValueChange = onValueChange,
        label = { Text(text = labelValue) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ), singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Rounded.Person, contentDescription = "Email Icon")
        },
        isError = isError && isFocused
    )

}

@Composable
fun IntValue(labelValue: String,textValue: String, onValueChange: (Int) -> Unit,  isError: Boolean = false){
    var isFocused by remember { mutableStateOf(false) }
    var textRepresentation by remember { mutableStateOf(textValue) }
    OutlinedTextField(
        value = textRepresentation,
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .onFocusChanged { isFocused = it.isFocused },
        onValueChange = { newValue ->
            runCatching {
                val isValid = newValue.toIntOrNull() != null // Check if it's a valid integer
                textRepresentation = newValue
                if (isValid) onValueChange(newValue.toInt())
            }.onFailure {
                // Handle invalid input (not a number)
            }
        },
        label = { Text(text = labelValue) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ), singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Rounded.Person, contentDescription = "Person's Icon")
        },
        isError = isError && isFocused
    )

}

fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Composable
fun EmailValue(labelValue: String,email: String, onValueChange: (String) -> Unit,  isError: Boolean = false){
    var isFocused by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = email,
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .onFocusChanged {
                isFocused = it.isFocused
                if (it.isFocused) { // Check validity when focus is lost
                    showError = !isValidEmail(email)
                }
            },
        onValueChange = onValueChange,
        label = { Text(text = labelValue) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ), singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Rounded.MailOutline, contentDescription = "Email Icon")
        },
        isError = isError && showError
    )
}

@Composable
fun PasswordValue(labelValue: String, password: String, onValueChange: (String) -> Unit,  isError: Boolean = false){
    var isFocused by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = password,
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .onFocusChanged { isFocused = it.isFocused },
        onValueChange = onValueChange,
        label = { Text(text = labelValue) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        ), singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Outlined.Lock, contentDescription = "Email Icon")
        },
        isError = isError && isFocused
    )
}

@Composable
fun LoginButton(email: String, password: String){
    val authViewModel : AuthViewModel = viewModel()
    ElevatedButton(onClick = {
        val isValid = email.isNotBlank() && password.isNotBlank()
        if(isValid){
            Log.d("valid","It is valid")
            authViewModel.login(email,password)
        }
        else{
            Log.d("not valid","It is not valid")
        }
    }) {
        Text(text = "Login",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            ), modifier = Modifier.fillMaxWidth(0.3f)
        )
    }
}

@Composable
fun RegisterButton(firstName: String, lastName: String, email: String, password: String){
    val authViewModel : AuthViewModel = viewModel()
    val context = LocalContext.current
    var permissionGranted by remember { mutableStateOf(false) }
    var showExplanation by remember { mutableStateOf(false) }
    if (showExplanation) {
        Text("Internet permission is required for registration.")
    }
    var isValid by remember { mutableStateOf(false) }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            permissionGranted = isGranted
            if (!isGranted) {
                showExplanation = true
            }
        }
    )

    ElevatedButton(onClick = {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET)
            == PackageManager.PERMISSION_GRANTED) {
            isValid = firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank() && password.isNotBlank()
            if(isValid){
                Log.d("valid","It is valid")
                authViewModel.register(email,password,firstName,lastName, context )
            }
            else{
                Log.d("not valid","It is not valid")
            }
        }
        else {
            requestPermissionLauncher.launch(Manifest.permission.INTERNET)
        }
    }

    ) {
        Text(text = "Register",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            ), modifier = Modifier.fillMaxWidth(0.3f)
        )
    }
}

@Composable
fun HandleRegistrationSuccess(firebaseUser: FirebaseUser?, navController: NavHostController) {
    LaunchedEffect(key1 = firebaseUser) {
        if (firebaseUser != null) {
            Log.d("navigating", "User registered")
            navController.navigate(Route.BottomNav.routes) {
                launchSingleTop = true
            }
        } else {
            Log.d("failed", "Registration failed")
        }
    }
}

@Composable
fun ParamsUpdateButton(nitrogen: Int, phosphorus: Int, potassium: Int, ph: Int, navController: NavHostController){
    val authViewModel : AuthViewModel = viewModel()
    val context = LocalContext.current
    val showExplanation by remember { mutableStateOf(false) }
    if (showExplanation) {
        Text("Internet permission is required for registration.")
    }
    var isValid by remember { mutableStateOf(false) }

//    Location
    val location = authViewModel.location.value
    val locationUtils = LocationUtils(context = context)

    val requestPermissionlauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if(permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
                &&
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
            ) {
                locationUtils.requestLocationUpdates(viewModel= authViewModel)
            }else{
                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            context ,
                            Manifest.permission.ACCESS_COARSE_LOCATION)
                if(rationaleRequired) {
                    Toast.makeText(context,"This feature Required Location Permission",Toast.LENGTH_LONG).show()
                }else {
                    Toast.makeText(context,"Please Enable Location Permission form Android",Toast.LENGTH_LONG).show()
                }
            }
        })


    ElevatedButton(onClick = {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION )
            == PackageManager.PERMISSION_GRANTED) {
            locationUtils.requestLocationUpdates(viewModel= authViewModel)
            isValid = nitrogen != 0 && phosphorus != 0 && potassium != 0 && ph != 0
            if(isValid){
                Log.d("valid","It is valid")
                authViewModel.updateParams(nitrogen,phosphorus,potassium,ph)
                if (location != null) {
                    try {
                        authViewModel.UpdateLocation(location.latitude,location.longitude)
                        authViewModel.UpdateExtraParams()
                    }finally {
                        Toast.makeText(context,"Parameters Updated",Toast.LENGTH_LONG).show()
                        navController.navigate(Route.CropRecommendations.routes)
                    }
                }
            }
            else{
                Log.d("not valid","It is not valid")
                Toast.makeText(context,"Please Enter valid Values In Numbers only",Toast.LENGTH_LONG).show()
            }
        }
        else {
            val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                context as MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        context ,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
            if(rationaleRequired) {
                Toast.makeText(context,"This feature Required Location Permission",Toast.LENGTH_LONG).show()
            }else {
                Toast.makeText(context,"Please Enable Location Permission form Android",Toast.LENGTH_LONG).show()
            }
            requestPermissionlauncher.launch(
                arrayOf(
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    ) {
        Text(text = "Update",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            ), modifier = Modifier.fillMaxWidth(0.3f)
        )
    }
}

//@Composable
//fun DisplayLocation(locationUtils: LocationUtils,viewModel: AuthViewModel,context: Context){
//    val location = viewModel.location.value
//
//    val State = location?.let {
//        locationUtils.requestGeocodeLocation(location)
//    }
//
//    val requestPermissionlauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestMultiplePermissions(),
//        onResult = { permissions ->
//            if(permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
//                &&
//                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
//                ) {
//                    locationUtils.requestLocationUpdates(viewModel= viewModel)
//            }else{
//                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
//                    context as MainActivity,
//                    Manifest.permission.ACCESS_FINE_LOCATION) ||
//                    ActivityCompat.shouldShowRequestPermissionRationale(
//                    context as MainActivity,
//                    Manifest.permission.ACCESS_COARSE_LOCATION)
//                if(rationaleRequired) {
//                        Toast.makeText(context,"This feature Required Location Permission",Toast.LENGTH_LONG).show()
//                }else {
//                    Toast.makeText(context,"Please Enable Location Permission form Android",Toast.LENGTH_LONG).show()
//                }
//            }
//        })
//    Column(modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ){
//
//        if (location != null){
//            Text(text = "Location:\n lat: ${location.latitude} \n Long: ${location.longitude} \n State: $State")
//        }else{
//            Text(text = "Location Not Available")
//        }
//        Button(onClick = {
//            if(locationUtils.hasLocationPermission(context)) {
//                requestPermissionlauncher.launch(
//                    arrayOf(
//                        Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.ACCESS_COARSE_LOCATION
//                    )
//                )
//            }else{
//                requestPermissionlauncher.launch(
//                    arrayOf(
//                        Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.ACCESS_COARSE_LOCATION
//                    )
//                )
//            }
//        }) {
//                Text(text = "Get Location")
//        }
//    }
//}

//@Composable
//fun LocationApp(){
//    val context = LocalContext.current
//    val locationUtils = LocationUtils(context)
//    DisplayLocation(locationUtils = locationUtils, viewModel = AuthViewModel() ,context = context)
//}

//Text Components
@Composable
fun LargeTextComponents(value:String) {
    Text(text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 0.dp)
            .padding(top = 1.dp, bottom = 30.dp),
        fontSize = 45.sp,
        fontFamily = FontFamily(Font(R.font.lobstertwo_regular)),
        textAlign = TextAlign.Center

    )
}

@Composable
fun CardHeading(value: String) {
    Text(
        text = value,
        fontFamily = FontFamily(Font(R.font.merriweather_light)),
        fontSize = 25.sp,
        modifier = Modifier
            .fillMaxWidth() // Make Text fill the card
            .padding(12.dp),
    )
}

@Composable
fun CardDesc(value: String) {
    Text(
        text = value,
        fontFamily = FontFamily(Font(R.font.poppins_light)),
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Justify,
        style = TextStyle(
            hyphens = Hyphens.Companion.Auto // Enable automatic hyphenation
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 2.dp),
    )
}

@Composable
fun CardSubHeading(value: String) {
    Text(
        text = value,
        fontFamily = FontFamily(Font(R.font.merriweather_light)),
        fontSize = 18.sp,
        modifier = Modifier
            .fillMaxWidth() // Make Text fill the card
            .padding(12.dp,1.dp,0.dp,8.dp),
    )
}
@Composable
fun CardDesc2(value: String) {
    Text(
        text = value,
        fontFamily = FontFamily(Font(R.font.poppins_light)),
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Justify,
        style = TextStyle(
            hyphens = Hyphens.Companion.Auto // Enable automatic hyphenation
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 2.dp,16.dp,7.dp),
    )
}

@Composable
fun CardSubHeading2(value: String) {
    Text(
        text = value,
        fontFamily = FontFamily(Font(R.font.merriweather_light)),
        fontSize = 16.sp,
        modifier = Modifier
            .fillMaxWidth() // Make Text fill the card
            .padding(12.dp,1.dp,0.dp,8.dp),
    )
}

@Composable
fun CircularBulletList(items: List<String>) {
    Column(modifier = Modifier.padding(12.dp,0.dp,0.dp,7.dp)) {
        items.forEach { item ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .size(10.dp)
                        .clip(CircleShape) // Use the dynamic shape
                        .background(MaterialTheme.colorScheme.inverseSurface)
                )
                Text(
                    text = item,
                    fontFamily = FontFamily(Font(R.font.poppins_light))
                )
            }
        }
    }
}

@Composable
fun CircleLoader(){
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
fun ClickableLoginTextComponent(navController : NavHostController){
    val signup = stringResource(id = R.string.signup)

    val annotatedString = buildAnnotatedString {
        withStyle(
            SpanStyle(fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
            fontSize = 12.sp,color = MaterialTheme.colorScheme.secondary)
        ){
            pushStringAnnotation(tag = "Initial-text", annotation = "Initial-text")
            append("Don't have an Account?")}
        withStyle(
            SpanStyle(fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
            fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
        ){
            pushStringAnnotation(tag = signup, annotation = "SignUp")
            append(signup)
        }
    }
    ClickableText(text = annotatedString , onClick = {
            offset -> annotatedString.getStringAnnotations(offset,offset)
        .firstOrNull()?.also {
                span ->  if (span.item == "SignUp") {
            navController.navigate(Route.Register.routes){
                launchSingleTop = true
            }
        }
        }
    })
}

@Composable
fun ClickableRegisterTextComponent(navController : NavHostController){
    val login = stringResource(R.string.log_in)

    val annotatedString = buildAnnotatedString {
        withStyle(
            SpanStyle(fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
            fontSize = 12.sp,color = MaterialTheme.colorScheme.secondary)
        ){
            pushStringAnnotation(tag = "Initial-text", annotation = "Initial-text")
            append("Already have an Account?")}
        withStyle(
            SpanStyle(fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
            fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
        ){
            pushStringAnnotation(tag = login, annotation = "LogIn")
            append(login)
        }
    }
    ClickableText(text = annotatedString , onClick = {
            offset -> annotatedString.getStringAnnotations(offset,offset)
        .firstOrNull()?.also {
                span ->  Log.d("ClickableTextComponent","{${span.item}}")
            if (span.item == "LogIn") {
                navController.navigate(Route.Login.routes){
                    launchSingleTop = true
                }
            }
        }
    })
}