package com.example.ticketyours.presentation.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ticketyours.presentation.components.FilledButton
import com.example.ticketyours.presentation.components.PasswordTextField
import com.example.ticketyours.presentation.components.TitleText
import com.example.ticketyours.presentation.navigation.Route

@Composable
fun PasswordPage(
    navController: NavController,
    firstName: String,
    lastName: String,
    phoneNumber: String,
    email: String,
    dob: String
){
    var passwordOnce by remember { mutableStateOf("") }
    var passwordTwice by remember { mutableStateOf("") }
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)){
        TitleText("Email Verification",
            modifier = Modifier.padding(16.dp),
        )
        PasswordTextField(
            value = passwordOnce,
            onValueChange = {passwordOnce = it})
        Spacer(modifier = Modifier.padding(16.dp))
        PasswordTextField(
            value = passwordTwice,
            onValueChange = {passwordTwice = it})
        Spacer(modifier = Modifier.padding(16.dp))
        FilledButton(
            text = "Continue",
            onClick = {
                if(passwordOnce.isNotEmpty() == passwordTwice.isNotEmpty()){
                    Log.d("check","$firstName \n $lastName \n$phoneNumber\n $email\n $dob")
                    navController.navigate(Route.TheaterRegistrationPage)
                }
            })
    }
}