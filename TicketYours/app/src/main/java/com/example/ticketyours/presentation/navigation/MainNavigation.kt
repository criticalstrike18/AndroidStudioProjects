package com.example.ticketyours.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.ticketyours.presentation.screen.PasswordPage
import com.example.ticketyours.presentation.screen.AuthenticationPage
import com.example.ticketyours.presentation.screen.DivisionDetailsPage
import com.example.ticketyours.presentation.screen.ScreenDetailsPage
import com.example.ticketyours.presentation.screen.SeatLayoutPage
import com.example.ticketyours.presentation.screen.TheaterRegisterPage

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Route.TheaterRegistrationPage) {
        composable<Route.AuthenticationPage> {
            AuthenticationPage(
                navController
            )
        }
//        composable<Route.LoginPage> {
//            LoginPage()
//        }
        composable<Route.SetPasswordPage> {
            val args = it.toRoute<Route.SetPasswordPage>()
            PasswordPage(
                navController,
                args.firstName,
                args.lastName,
                args.phoneNumber,
                args.email,
                args.dob
            )
        }
        composable<Route.TheaterRegistrationPage> {
            TheaterRegisterPage(
                navController
            )
        }
        composable<Route.ScreenDetailsPage> {
            val args = it.toRoute<Route.ScreenDetailsPage>()
            ScreenDetailsPage(
                navController,
                args.currentScreenNo,
                args.totalScreens
            )
        }
        composable<Route.ScreenDivisionDetailsPage> {
            val args = it.toRoute<Route.ScreenDivisionDetailsPage>()
            DivisionDetailsPage(
                navController,
                args.currentScreenNo,
                args.totalScreens,
                args.maxSeatsPerRow,
                args.currentDivisionNo
            )
        }
        composable<Route.ScreenSeatLayoutEditor> {
            val args = it.toRoute<Route.ScreenSeatLayoutEditor>()
            SeatLayoutPage(
                navController,
                args.currentScreenNo,
                args.totalScreens,
                args.key,
                args.maxSeatsPerRow
            )
        }
    }

}
