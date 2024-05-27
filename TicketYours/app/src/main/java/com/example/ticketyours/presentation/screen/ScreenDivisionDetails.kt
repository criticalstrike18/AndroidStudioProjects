package com.example.ticketyours.presentation.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.ticketyours.data.model.Division
import com.example.ticketyours.presentation.cache.PreferencesManager
import com.example.ticketyours.presentation.cache.rememberTempDataCache
import com.example.ticketyours.presentation.components.FilledButton
import com.example.ticketyours.presentation.components.LargeText
import com.example.ticketyours.presentation.components.NormalTextField
import com.example.ticketyours.presentation.components.NumberSelector
import com.example.ticketyours.presentation.components.TitleText
import com.example.ticketyours.presentation.navigation.Route
import kotlinx.coroutines.launch

@Composable
fun DivisionDetailsPage(
    navController: NavController,
    currentScreenNo: Int,
    totalScreens: Int,
    maxSeatsPerRow: Int,
    currentDivisionNo: Int,
) {
    var divisionName by remember { mutableStateOf("") }
    var rows by remember { mutableIntStateOf(1) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val preferencesManager = remember { PreferencesManager(context) }
    val totalDivisions by preferencesManager.totalDivisionsFlow.collectAsState(initial = 1)

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        val screenNo = 1
        val divisionNo = 1
        TitleText(value = "Screen- $screenNo   Division- $divisionNo",
            modifier = Modifier.padding(14.dp))
        NormalTextField(value = divisionName,
            labelValue = "Division Name",
            keyboardType = KeyboardType.Text,
            onValueChange = { divisionName = it })
        LargeText(value = "Total Number of Rows",
            modifier = Modifier.padding(14.dp))
        NumberSelector(value = rows,
            onValueChange = { rows = it },
            minValue = 1)
        Spacer(modifier = Modifier.padding(16.dp))
        FilledButton(text = "Continue",
            onClick = {
                scope.launch {
                    if (currentDivisionNo < totalDivisions) {
                        navController.navigate(
                            Route.ScreenDivisionDetailsPage(
                                currentScreenNo = currentScreenNo,
                                totalScreens = totalScreens,
                                maxSeatsPerRow = maxSeatsPerRow,
                                currentDivisionNo = currentDivisionNo + 1
                            )
                        )
                    } else {
                        navController.navigate(
                            Route.ScreenSeatLayoutEditor(
                                currentScreenNo = currentScreenNo,
                                totalScreens = totalScreens,
                                key = "divisionList",
                                maxSeatsPerRow = maxSeatsPerRow,
                            )
                        )
                    }
                }
            }
        )
    }
}