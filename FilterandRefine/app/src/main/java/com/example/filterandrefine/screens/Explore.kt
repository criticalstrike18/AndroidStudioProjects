package com.example.filterandrefine.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.filterandrefine.components.TopHeader
import com.example.filterandrefine.navBar.TopTabBar

@Composable
fun ExploreScreen(navController: NavHostController){
    Column(modifier = Modifier.fillMaxSize()
        .padding(top = 16.dp),
    ) {
        TopHeader(navController)
        TopTabBar(navControllerOg = navController)
    }
}