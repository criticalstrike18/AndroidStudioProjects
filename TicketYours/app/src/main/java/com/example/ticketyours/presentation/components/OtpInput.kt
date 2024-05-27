package com.example.ticketyours.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun OtpInputField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    onOtpTextChange: (String, Boolean) -> Unit,
    onResendCode: () -> Unit
) {
    var focusedIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(otpText) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }
        focusedIndex = otpText.length
    }

    Column(modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(
            "Enter your OTP code",
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        BasicTextField(
            value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
            onValueChange = {
                if (it.text.length <= otpCount) {
                    onOtpTextChange.invoke(it.text, it.text.length == otpCount)
                    focusedIndex = it.text.length
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            decorationBox = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    repeat(otpCount) { index ->
                        CharView(
                            index = index,
                            text = otpText,
                            isFocused = index == focusedIndex
                        )
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        SeparateSingleText(initialText = "Didn't Receive code? ",
            highlightText = "Resend Again",
            onResendCode = onResendCode,
            annotation = "RESEND")
    }
}

@Composable
private fun CharView(
    index: Int,
    text: String,
    isFocused: Boolean
) {
    val char = when {
        index < text.length -> text[index].toString()
        else -> ""
    }

    Box(
        modifier = Modifier
            .size(48.dp)
            .border(
                width = 1.dp,
                color = when {
                    isFocused -> MaterialTheme.colorScheme.primary
                    char.isNotEmpty() -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                },
                shape = MaterialTheme.shapes.small
            )
    ) {
        Text(
            text = char,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
        )
    }
}

