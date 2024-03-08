package com.example.threadsclone.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.threadsclone.screens.AddThread
import com.example.threadsclone.screens.BottomNav
import com.example.threadsclone.screens.Login
import com.example.threadsclone.screens.Notification
import com.example.threadsclone.screens.Register
import com.example.threadsclone.screens.Splash
import com.example.threadsclone.screens.profile

@Composable
fun NavGraph(navController: NavHostController){
        
    NavHost(navController = navController, startDestination = Route.Splash.routes) {
        composable(Route.Splash.routes, enterTransition = { fadeIn() }, exitTransition = { fadeOut() }) {
            Splash(navController)
        }


        composable(Route.BottomNav.routes, enterTransition = { fadeIn() }, exitTransition = { fadeOut() }) {
            BottomNav(navController)
        }



        composable(Route.Profile.routes) {
            profile(navController)
        }



        composable(Route.Notification.routes) {
            Notification()
        }



        composable(Route.AddThread.routes) {
            AddThread()
        }

        composable(Route.Login.routes, enterTransition = { fadeIn() }, exitTransition = { fadeOut() }) {
            Login(navController)
        }

        composable(Route.Register.routes, enterTransition = { fadeIn() }, exitTransition = { fadeOut() }) {
            Register(navController)
        }
    }
}