package com.example.threadsclone.ViewModel

import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.threadsclone.model.userModel
import com.example.threadsclone.utils.SharedPrefs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.storage

class AuthViewModel : ViewModel() {

    val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    val userRef = db.getReference("users")

    private val storageRef = Firebase.storage.reference

    private val _firebaseUser = MutableLiveData<FirebaseUser>()
    val firebaseUser : LiveData<FirebaseUser> = _firebaseUser

    private val _error = MutableLiveData<String>()
    val error : LiveData<String> = _error

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
        userRef.child(uid!!).setValue(userData)
            .addOnSuccessListener {
                SharedPrefs.storeData(firstName,lastName,email,context)
            }.addOnFailureListener {

            }
    }

    fun logout() {
        auth.signOut()
        _firebaseUser.postValue(null)

    }

}
