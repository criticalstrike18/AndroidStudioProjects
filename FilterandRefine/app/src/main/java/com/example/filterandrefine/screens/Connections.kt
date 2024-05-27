package com.example.filterandrefine.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.filterandrefine.components.TopHeader

@Composable
fun ConnectionsScreen(navController: NavHostController){
    Column(modifier = Modifier.fillMaxSize()
    ) {
        TopHeader(navController)
    }
}