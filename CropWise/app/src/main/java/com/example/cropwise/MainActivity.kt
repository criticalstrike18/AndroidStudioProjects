package com.example.cropwise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.cropwise.ViewModel.AuthViewModel
import com.example.cropwise.navigation.LoginNavigation
import com.example.cropwise.navigation.Navigation
import com.example.cropwise.navigation.Route
import com.example.cropwise.screens.BottomNavBar
import com.example.cropwise.screens.Login
import com.example.cropwise.ui.theme.CropWiseTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        FirebaseApp.initializeApp(this)
        setContent {
            CropWiseTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val viewModel = AuthViewModel()
                    val currentUser by viewModel.currentUser.observeAsState()
                    if (currentUser != null) {
                        BottomNavBar(navController = navController)
                    } else {
                        Navigation(navController = navController,Route.Login)
                    }


                }
            }
        }
    }
}
