package com.example.threadsclone.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.threadsclone.ViewModel.AuthViewModel
import com.example.threadsclone.navigation.Route


@Composable
fun profile(navController:NavHostController) {
    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)

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
    Text(text = "This is a profile page", modifier = Modifier.clickable {
        authViewModel.logout()
    })
}