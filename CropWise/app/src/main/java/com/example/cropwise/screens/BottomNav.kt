package com.example.cropwise.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Agriculture
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cropwise.ViewModel.AuthViewModel
import com.example.cropwise.model.BottomNavItem
import com.example.cropwise.navigation.Route

@Composable
fun BottomNav(navController: NavHostController){

    val navController1 = rememberNavController()

    Scaffold(bottomBar = { MyBottomBar(navController1) }) { innerPadding ->
        NavHost(navController = navController1,
            startDestination = Route.Home.routes,
            modifier = Modifier.padding(innerPadding)) {
            composable(route = Route.Home.routes) {
                Home(navController)
            }

            composable(route = Route.CropRecommendations.routes) {
                CropRecommendations(viewModel = AuthViewModel(),navController)
            }

            composable(route = Route.PricePrediction.routes) {
                PricePrediction()
            }

            composable(route = Route.ParamsUpdater.routes) {
                ParamsUpdater(navController)
            }

            composable(route = Route.Logout.routes) {
                Logout(navController)
            }
        }
    }
}

@Composable
fun MyBottomBar(navController1: NavHostController) {

    val backStackEntry = navController1.currentBackStackEntryAsState()

    val list = listOf(
        BottomNavItem(
            "Home",
            Route.Home.routes,
            Icons.Rounded.Home
        ),
        BottomNavItem(
            "Crop Recommendations",
            Route.CropRecommendations.routes,
            Icons.Rounded.Agriculture
        ),
        BottomNavItem(
            "Price Prediction",
            Route.PricePrediction.routes,
            Icons.Rounded.ShoppingCart
        ),
        BottomNavItem(
            "Params Updater",
            Route.ParamsUpdater.routes,
            Icons.Rounded.Person
        ),
//        BottomNavItem(
//            "Logout",
//            Route.Logout.routes,
//            Icons.Rounded.Close
//        ),




        )

    BottomAppBar {
        list.forEach {
            val selected = it.route == backStackEntry.value?.destination?.route

            NavigationBarItem(selected = selected,
                onClick = { navController1.navigate(it.route) {
                    popUpTo(navController1.graph.findStartDestination().id){
                        saveState = true
                    }
                    launchSingleTop = true
                }
                },
                icon = {
                    Icon(imageVector = it.icon, contentDescription = it.title)
                })
        }
    }
}
