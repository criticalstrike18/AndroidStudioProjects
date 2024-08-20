package com.example.ticketyours.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EventSeat
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ticketyours.presentation.cache.PreferencesManager
import com.example.ticketyours.presentation.components.FilledButton
import com.example.ticketyours.presentation.components.LargeText
import com.example.ticketyours.presentation.navigation.Route
import kotlinx.coroutines.launch

@Composable
fun ViewSeatLayoutScreen(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val preferencesManager = remember { PreferencesManager(context) }
    val layoutData by preferencesManager.layoutDataFlow.collectAsState(initial = null)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (layoutData != null) {
            LargeText(value = "Theater Layout",
                modifier = Modifier.padding(14.dp))
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                layoutData?.divisions?.forEach { division ->
                    item {
                        Text(
                            text = division.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .align(Alignment.CenterHorizontally)
                                .background(Color.LightGray)
                        )
                    }
                    items(division.rows) { row ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            row.seats.forEach { seat ->
                                Icon(
                                    imageVector = Icons.Filled.EventSeat,
                                    contentDescription = "Seat",
                                    tint = if (seat.isMarked) Color.Red else Color.Green,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .padding(2.dp)
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            FilledButton(text = "Continue",
                onClick = {
                    scope.launch {
                        preferencesManager.clearDataStore()
                        navController.navigate(Route.AuthenticationPage)
                    }

                }
            )
        } else {
            Text("Loading layout...")
        }
    }
}
