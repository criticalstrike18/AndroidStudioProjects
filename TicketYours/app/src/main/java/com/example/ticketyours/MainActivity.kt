package com.example.ticketyours

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.ticketyours.presentation.navigation.MainNavigation
import com.example.ticketyours.ui.theme.TicketYoursTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicketYoursTheme {
               MainNavigation()
            }
        }
    }
}
