package com.example.filterandrefine.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.filterandrefine.screens.BusinessesScreen
import com.example.filterandrefine.screens.ChatScreen
import com.example.filterandrefine.screens.ConnectionsScreen
import com.example.filterandrefine.screens.ContactsScreen
import com.example.filterandrefine.screens.ExploreScreen
import com.example.filterandrefine.screens.GroupsScreen
import com.example.filterandrefine.screens.PersonalScreen
import com.example.filterandrefine.screens.RefineScreen
import com.example.filterandrefine.screens.ServicesScreen

@Composable
fun Navigation(navController:NavHostController) {

    NavHost(navController = navController, startDestination = ScreenRoute.ExploreScreen.route) {
        composable(ScreenRoute.ExploreScreen.route) {
            ExploreScreen(navController)
        }

        composable(ScreenRoute.RefineScreen.route) {
            RefineScreen(navController)
        }

        composable(ScreenRoute.ConnectionsScreen.route) {
            ConnectionsScreen(navController)
        }

        composable(ScreenRoute.ChatScreen.route) {
            ChatScreen(navController)
        }

        composable(ScreenRoute.ContactsScreen.route) {
            ContactsScreen(navController)
        }

        composable(ScreenRoute.GroupsScreen.route) {
            GroupsScreen(navController)
        }
    }
}

@Composable
fun TopBarNavigation(navController:NavHostController) {
    NavHost(navController = navController, startDestination = ScreenRoute.PersonalScreen.route){
        composable(ScreenRoute.PersonalScreen.route) {
            PersonalScreen(navController)
        }
        composable(ScreenRoute.ServicesScreen.route) {
            ServicesScreen(navController)
        }
        composable(ScreenRoute.BusinessesScreen.route) {
            BusinessesScreen(navController)
        }
    }
}
