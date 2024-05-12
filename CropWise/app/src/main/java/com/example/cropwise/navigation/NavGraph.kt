package com.example.cropwise.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cropwise.ViewModel.AuthViewModel
import com.example.cropwise.screens.BottomNav
import com.example.cropwise.screens.CropRecommendations
import com.example.cropwise.screens.Login
import com.example.cropwise.screens.Logout
import com.example.cropwise.screens.ParamsUpdater
import com.example.cropwise.screens.PricePrediction
import com.example.cropwise.screens.Register
import com.example.cropwise.screens.Splash

@Composable
fun NavGraph(navController: NavHostController){

    NavHost(navController = navController, startDestination = Route.Splash.routes) {
        composable(Route.Splash.routes, enterTransition = { fadeIn() }, exitTransition = { fadeOut() }) {
            Splash(navController)
        }


        composable(Route.BottomNav.routes, enterTransition = { fadeIn() }, exitTransition = { fadeOut() }) {
            BottomNav(navController)
        }



        composable(Route.ParamsUpdater.routes) {
            ParamsUpdater(navController)
        }



        composable(Route.Logout.routes) {
            Logout(navController)
        }

        composable(Route.CropRecommendations.routes) {
            CropRecommendations(viewModel = AuthViewModel(),navController)
        }


        composable(Route.PricePrediction.routes) {
            PricePrediction()
        }

        composable(Route.Login.routes, enterTransition = { fadeIn() }, exitTransition = { fadeOut() }) {
            Login(navController)
        }

        composable(Route.Register.routes, enterTransition = { fadeIn() }, exitTransition = { fadeOut() }) {
            Register(navController)
        }
    }
}