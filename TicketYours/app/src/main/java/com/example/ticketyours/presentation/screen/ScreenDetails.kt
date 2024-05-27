package com.example.ticketyours.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ticketyours.presentation.cache.PreferencesManager
import com.example.ticketyours.presentation.components.FilledButton
import com.example.ticketyours.presentation.components.LargeText
import com.example.ticketyours.presentation.components.NormalTextField
import com.example.ticketyours.presentation.components.NumberSelector
import com.example.ticketyours.presentation.navigation.Route
import kotlinx.coroutines.launch

@Composable
fun ScreenDetailsPage(
    navController: NavController,
    currentScreenNo: Int,
    totalScreens: Int
){
    var screenName by remember { mutableStateOf("") }
    var seatDivisions by remember { mutableIntStateOf(1) }
    var totalSeats by remember { mutableIntStateOf(1) }
    var maxSeats by remember { mutableIntStateOf(1) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val preferencesManager = remember { PreferencesManager(context) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        val screenNo = 1
        LargeText(value = "Screen No -> $screenNo",
            modifier = Modifier.padding(14.dp))
        NormalTextField(value = screenName,
            labelValue = "Screen Name",
            keyboardType = KeyboardType.Text,
            onValueChange = { screenName = it })
        LargeText(value = "Number of Divisions",
            modifier = Modifier.padding(14.dp))
        NumberSelector(value = seatDivisions,
            onValueChange = { seatDivisions = it },
            minValue = 1)
        LargeText(value = "Total Number of Seats",
            modifier = Modifier.padding(14.dp))
        NumberSelector(value = totalSeats,
            onValueChange = { totalSeats = it },
            minValue = 1)
        LargeText(value = "Maximum Number of Seats",
            modifier = Modifier
                .padding(top = 14.dp,
                    start = 14.dp,
                    end = 14.dp))
        LargeText(value = "(Per Row)",
            modifier = Modifier.padding(bottom = 14.dp) )
        NumberSelector(value = maxSeats,
            onValueChange = { maxSeats = it },
            minValue = 1)
        Spacer(modifier = Modifier.padding(16.dp))
        FilledButton(text = "Continue",
            onClick = {
                scope.launch {
                    preferencesManager.setCurrentDivisionNo(1)
                    preferencesManager.setTotalDivisions(seatDivisions)
                    navController.navigate(Route.ScreenDivisionDetailsPage(
                        currentScreenNo = 1,
                        totalScreens = 1,
                        maxSeatsPerRow = maxSeats,
                        currentDivisionNo = 1,
                    ))
                }
            }
        )
    }
}