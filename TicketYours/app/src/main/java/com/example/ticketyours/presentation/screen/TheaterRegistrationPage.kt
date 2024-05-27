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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ticketyours.presentation.components.FilledButton
import com.example.ticketyours.presentation.components.LargeText
import com.example.ticketyours.presentation.components.NormalTextField
import com.example.ticketyours.presentation.components.NumberSelector
import com.example.ticketyours.presentation.components.TitleText
import com.example.ticketyours.presentation.navigation.Route

@Composable
fun TheaterRegisterPage(
    navController: NavController
){
    var theaterName by remember { mutableStateOf("") }
    var screen by remember { mutableIntStateOf(1) }
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)){
        TitleText("Theater Registration",
            modifier = Modifier.padding(16.dp),
        )
        NormalTextField(
            value = theaterName,
            labelValue = "Theater Name",
            keyboardType = KeyboardType.Text,
            onValueChange = {theaterName = it})
        LargeText(value = "Number of Screens",
            modifier = Modifier.padding(14.dp)
        )
        NumberSelector(
            value = screen,
            onValueChange = {screen = it},
            minValue = 1)

        Spacer(modifier = Modifier.padding(16.dp))
        FilledButton(
            text = "Continue",
            onClick = {
                navController.navigate(Route.ScreenDetailsPage(
                    currentScreenNo = 1,
                    totalScreens = 1,
                ))
            }
        )
    }
}