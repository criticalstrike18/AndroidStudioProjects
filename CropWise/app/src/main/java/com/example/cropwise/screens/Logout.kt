package com.example.cropwise.screens

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cropwise.ViewModel.AuthViewModel
import com.example.cropwise.navigation.Route

@Composable
fun Logout(navController : NavHostController) {

    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)

    LaunchedEffect(key1 = Unit) { // `Unit` triggers the effect once
        authViewModel.logout()
    }

    LaunchedEffect(key1 = firebaseUser) {
        if (firebaseUser == null) {
            navController.navigate(Route.Login.routes) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        } else {
            Log.d("failed", "Registration failed")
        }
    }

}