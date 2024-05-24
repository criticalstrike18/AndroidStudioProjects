package com.example.filterandrefine.navBar

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Diversity1
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.outlined.Contacts
import androidx.compose.material.icons.outlined.Diversity1
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.RemoveRedEye
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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.filterandrefine.data.bottomNavItem
import com.example.filterandrefine.navigation.Navigation
import com.example.filterandrefine.navigation.ScreenRoute

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavBar(){
    val navController = rememberNavController()
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    val screensWithBottomBar = listOf(
        ScreenRoute.ExploreScreen.route,
        ScreenRoute.ConnectionsScreen.route,
        ScreenRoute.ChatScreen.route,
        ScreenRoute.ContactsScreen.route,
        ScreenRoute.GroupsScreen.route
    )
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val items = listOf(
        bottomNavItem(
            name = "Explore",
            route = ScreenRoute.ExploreScreen.route,
            selectedIcon = Icons.Filled.RemoveRedEye,
            unselectedIcon = Icons.Outlined.RemoveRedEye,
        ),
        bottomNavItem(
            name = "Connections",
            route = ScreenRoute.ConnectionsScreen.route,
            selectedIcon = Icons.Filled.Diversity1,
            unselectedIcon = Icons.Outlined.Diversity1,
        ),
        bottomNavItem(
            name = "Chat",
            route = ScreenRoute.ChatScreen.route,
            selectedIcon = Icons.AutoMirrored.Filled.Chat,
            unselectedIcon = Icons.AutoMirrored.Outlined.Chat,
        ),
        bottomNavItem(
            name = "Contacts",
            route = ScreenRoute.ContactsScreen.route,
            selectedIcon = Icons.Filled.Contacts,
            unselectedIcon = Icons.Outlined.Contacts,
        ),
        bottomNavItem(
            name = "Groups",
            route = ScreenRoute.GroupsScreen.route,
            selectedIcon = Icons.Filled.Groups,
            unselectedIcon = Icons.Outlined.Groups,
        ),
    )
    Scaffold(
        bottomBar = {
            if (currentRoute in screensWithBottomBar) {
                NavigationBar {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                navController.navigate(item.route)
                            },
                            label = {
                                Text(
                                    text = item.name,
                                    fontSize = 10.sp,
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = if (index == selectedItem) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.name
                                )
                            }
                        )
                    }
                }
            }
        }
    ) {
        Navigation(navController = navController)
    }
}

