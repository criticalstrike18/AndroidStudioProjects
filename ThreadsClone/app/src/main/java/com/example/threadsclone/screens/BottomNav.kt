package com.example.threadsclone.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.threadsclone.model.BottomNavItem
import com.example.threadsclone.navigation.Route


@Composable
fun BottomNav(navController: NavHostController){

    val navController1 = rememberNavController()

    Scaffold(bottomBar = { MyBottomBar(navController1) }) { innerPadding ->
    NavHost(navController = navController1,
        startDestination = Route.Home.routes,
        modifier = Modifier.padding(innerPadding)) {
        composable(route = Route.Home.routes) {
            Home()
        }

        composable(route = Route.Search.routes) {
            Search()
        }

        composable(route = Route.AddThread.routes) {
            AddThread()
        }

        composable(route = Route.Profile.routes) {
            profile(navController)
        }

        composable(route = Route.Notification.routes) {
            Notification()
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
            "Search",
            Route.Search.routes,
            Icons.Rounded.Search
        ),
        BottomNavItem(
            "Add Threads",
            Route.AddThread.routes,
            Icons.Rounded.Add
        ),
        BottomNavItem(
            "Notification",
            Route.Notification.routes,
            Icons.Rounded.Notifications
        ),
        BottomNavItem(
            "Profile",
            Route.Profile.routes,
            Icons.Rounded.Person
        ),




    )

    BottomAppBar {
        list.forEach {
            val selected = it.route == backStackEntry?.value?.destination?.route
            
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