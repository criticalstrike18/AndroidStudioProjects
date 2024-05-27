package com.example.ticketyours.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ticketyours.ui.theme.fontFamily

@Composable
fun NumberSelector(
    value: Int,
    modifier: Modifier = Modifier,
    onValueChange: (Int) -> Unit,
    minValue: Int = 0,
    maxValue: Int = Int.MAX_VALUE,
) {
    var textFieldValue by remember { mutableStateOf(value.toString()) }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Left (decrease) button
        if (value > minValue) {
            IconButton(onClick = {
                val newValue = (value - 1).coerceAtLeast(minValue)
                onValueChange(newValue)
                textFieldValue = newValue.toString()
            }) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Decrease",
                    Modifier.size(50.dp))
            }
        } else {
            // Spacer to maintain layout when button is hidden
            Spacer(modifier = Modifier.size(48.dp))
        }

        // Number display and edit field
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { newValue ->
                textFieldValue = newValue
                newValue.toIntOrNull()?.let { intValue ->
                    if (intValue in minValue..maxValue) {
                        onValueChange(intValue)
                    }
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier
                .width(80.dp)
                .height(80.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color.Transparent, unfocusedBorderColor = Color.Transparent),
            textStyle = TextStyle(textAlign = TextAlign.Center,
                color = Color.DarkGray,
                fontSize = 36.sp)
        )

        // Right (increase) button
        IconButton(
            onClick = {
                val newValue = (value + 1).coerceAtMost(maxValue)
                onValueChange(newValue)
                textFieldValue = newValue.toString()
            },
            enabled = value < maxValue
        ) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Increase",
                Modifier.size(50.dp))
        }
    }
}