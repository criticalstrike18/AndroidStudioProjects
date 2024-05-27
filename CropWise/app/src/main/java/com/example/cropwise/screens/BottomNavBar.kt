package com.example.cropwise.screens

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Agriculture
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ModeEdit
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cropwise.model.BottomNavItem
import com.example.cropwise.navigation.Navigation
import com.example.cropwise.navigation.Route

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavBar(navController: NavHostController){
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val items = listOf(
        BottomNavItem(
            "Home",
            Route.Home.routes,
            Icons.Filled.Home,
            Icons.Outlined.Home
        ),
        BottomNavItem(
            "Crop Recommendations",
            Route.CropRecommendations.routes,
            Icons.Filled.Agriculture,
            Icons.Outlined.Agriculture
        ),
        BottomNavItem(
            "Price Prediction",
            Route.PricePrediction.routes,
            Icons.Filled.ShoppingCart,
            Icons.Outlined.ShoppingCart
        ),
        BottomNavItem(
            "Params Updater",
            Route.ParamsUpdater.routes,
            Icons.Filled.ModeEdit,
            Icons.Outlined.ModeEdit
        )
    )
    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    val isSelected = currentDestination?.route == item.route
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            navController.navigate(item.route)
                        },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        }
                    )

                }
            }
        }
    ) {
        Navigation(navController,Route.Home)
    }
}