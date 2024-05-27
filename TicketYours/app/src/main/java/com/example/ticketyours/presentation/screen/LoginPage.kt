package com.example.ticketyours.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ticketyours.presentation.components.FilledButton
import com.example.ticketyours.presentation.components.NormalTextField
import com.example.ticketyours.presentation.components.PasswordTextField
import com.example.ticketyours.ui.theme.fontFamily

@Composable
fun LoginPage(){
    var phoneno by remember {mutableStateOf("")}
    var password by remember {mutableStateOf("")}
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 48.dp, start = 16.dp, end = 12.dp)){
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = "BackArrow")
            Text(
                text = "Back",
                fontFamily = fontFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(8.dp)
            )
        }
        Text(text = "Sign Up with your Email or Phone Number",
            fontFamily = fontFamily,
            fontSize = 24.sp,
            lineHeight = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(12.dp))
        NormalTextField(
            value = phoneno,
            labelValue = "Phone Number",
            keyboardType = KeyboardType.Number,
            onValueChange = {phoneno = it})
        PasswordTextField(
            value = password,
            onValueChange = {password = it})
        FilledButton(text = "Sign In", onClick = {})
    }
}
