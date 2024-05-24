package com.example.filterandrefine.data

import androidx.compose.ui.graphics.vector.ImageVector

data class bottomNavItem(
    val name: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)
