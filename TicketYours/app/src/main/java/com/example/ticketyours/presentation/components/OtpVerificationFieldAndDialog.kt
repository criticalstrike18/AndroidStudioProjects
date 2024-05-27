package com.example.ticketyours.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun VerifiableTextField(
    value: String,
    labelValue: String,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showOtpDialog by remember { mutableStateOf(false) }
    var isVerified by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = { if (!isVerified) onValueChange(it) },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        placeholder = {
            Text(
                labelValue,
                color = Color.DarkGray,
                fontSize = 16.sp
            )
        },
        label = { Text(labelValue) },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Next
        ),
        shape = RoundedCornerShape(8.dp),
        textStyle = TextStyle(fontSize = 16.sp),
        singleLine = true,
        trailingIcon = {
            if (isVerified) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Verified",
                    tint = Color.Green
                )
            } else {
                Button(
                    onClick = {
                        showOtpDialog = true
                    },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text("Verify")
                }
            }
        },
        enabled = !isVerified
    )

    if (showOtpDialog) {
        OtpVerificationDialog(
            onDismiss = { showOtpDialog = false },
            onVerify = { isVerified = true }
        )
    }
}

@Composable
fun OtpVerificationDialog(
    onDismiss: () -> Unit,
    onVerify: (String) -> Unit
) {
    var otpValue by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Enter OTP",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    IconButton(
                        onClick = onDismiss,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close"
                        )
                    }
                }


                Row(Modifier.fillMaxHeight(.25f)) {
                    OtpInputField(
                        otpText = otpValue,
                        onOtpTextChange = { newOtp, isComplete ->
                            otpValue = newOtp
                            if (isComplete) {
                                onVerify(newOtp)
                                onDismiss()
                            }
                        },
                        onResendCode = { /* Handle resend code logic */ }
                    )
                }
                Button(
                    onClick = {
                        onVerify(otpValue)
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Verify")
                }
            }
        }
    }
}