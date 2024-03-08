package com.example.threadsclone.model

import android.icu.text.CaseMap.Title
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.threadsclone.navigation.Route

data class BottomNavItem(
    val title: String,
    val route: String,
    val icon: ImageVector
)
