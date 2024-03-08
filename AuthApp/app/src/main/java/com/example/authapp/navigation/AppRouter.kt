package com.example.authapp.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen {
    data object signUpScreen : Screen()
    data object TermsandConditionsScreen : Screen()
    data object loginScreen : Screen()

}


object AppRouter {
    var currentScreen : MutableState<Screen> = mutableStateOf(Screen.signUpScreen)

    fun navigate(destination : Screen) {
        currentScreen.value = destination
    }
}