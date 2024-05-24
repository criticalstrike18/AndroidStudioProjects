package com.example.filterandrefine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.filterandrefine.navBar.BottomNavBar
import com.example.filterandrefine.ui.theme.FilterandRefineTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FilterandRefineTheme {
                BottomNavBar()
            }
        }
    }
}