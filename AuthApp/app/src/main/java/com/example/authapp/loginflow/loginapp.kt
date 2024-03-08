package com.example.authapp.loginflow

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.authapp.navigation.AppRouter.currentScreen
import com.example.authapp.navigation.Screen
import com.example.authapp.screens.SignUpScreen
import com.example.authapp.screens.TermsandConditionsScreen

@Composable
fun LoginApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Crossfade(targetState = currentScreen, label = "") { currentScreen ->
            when(currentScreen.value){
                is Screen.signUpScreen -> {
                    SignUpScreen()
                }
                is Screen.TermsandConditionsScreen -> {
                    TermsandConditionsScreen()
                }
                else -> {
                    TermsandConditionsScreen()
                }
            }

        }
    }
}