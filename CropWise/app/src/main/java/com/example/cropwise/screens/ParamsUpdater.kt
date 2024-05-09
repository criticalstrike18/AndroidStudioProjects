package com.example.cropwise.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cropwise.R
import com.example.cropwise.components.IntValue
import com.example.cropwise.components.LargeTextComponents
import com.example.cropwise.components.ParamsUpdateButton


@Composable
fun ParamsUpdater(navController: NavHostController) {
    var Nitrogen by remember { mutableStateOf("") }
    var Phosphorus by remember { mutableStateOf("") }
    var Potassium by remember { mutableStateOf("") }
    var Ph by remember { mutableStateOf("") }
//    var isLoading by remember { mutableStateOf(false) }
//
//    if (isLoading) {   // Show progress indicator when loading
//        CircularProgressIndicator(
//            modifier = Modifier.width(64.dp),
//            color = MaterialTheme.colorScheme.secondary,
//            trackColor = MaterialTheme.colorScheme.surfaceVariant,
//        )
//    }
//    else {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Row {
                LargeTextComponents("Soil Parameters")
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Row {
                IntValue(
                    labelValue = stringResource(R.string.nitrogen),
                    textValue = Nitrogen,
                    onValueChange = { newValue ->
                        runCatching {
                            Nitrogen = newValue.toString()  // Convert input string to integer
                        }.onFailure {
                            // Handle invalid input (not a number)
                        }
                    },
                    isError = Nitrogen == ""
                )
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Row {
                IntValue(
                    labelValue = stringResource(R.string.phosphorus),
                    textValue = Phosphorus,
                    onValueChange = { newValue ->
                        runCatching {
                            Phosphorus = newValue.toString()  // Convert input string to integer
                        }.onFailure {
                            // Handle invalid input (not a number)
                        }
                    },
                    isError = Phosphorus == ""
                )
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Row {
                IntValue(
                    labelValue = stringResource(R.string.pottasium),
                    textValue = Potassium,
                    onValueChange = { newValue ->
                        runCatching {
                            Potassium = newValue.toString()  // Convert input string to integer
                        }.onFailure {
                            // Handle invalid input (not a number)
                        }
                    },
                    isError = Potassium == ""
                )
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Spacer(modifier = Modifier.padding(5.dp))
            Row {
                IntValue(
                    labelValue = "Ph",
                    textValue = Ph,
                    onValueChange = { newValue ->
                        runCatching {
                            Ph = newValue.toString()  // Convert input string to integer
                        }.onFailure {
                            // Handle invalid input (not a number)
                        }
                    },
                    isError = Ph == ""
                )
            }
            Spacer(modifier = Modifier.padding(18.dp))
            Row {
                ParamsUpdateButton(
                    nitrogen = validateInput(Nitrogen) ?: 0,
                    phosphorus = validateInput(Phosphorus) ?: 0,
                    potassium = validateInput(Potassium) ?: 0,
                    ph = validateInput(Ph) ?: 0,
                    navController = navController,
//                    onClick = {isLoading = true}
                )
            }

        }
    }
//}

private fun validateInput(textValue: String): Int? {
    return textValue.toIntOrNull()  // Returns null if not a valid number
}