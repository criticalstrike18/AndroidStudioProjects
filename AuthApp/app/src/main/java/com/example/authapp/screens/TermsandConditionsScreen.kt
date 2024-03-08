package com.example.authapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.authapp.R
import com.example.authapp.components.LargeTextComponents
import com.example.authapp.navigation.AppRouter
import com.example.authapp.navigation.Screen
import com.example.authapp.navigation.SystemBackButtonHandler

@Preview(showBackground = true)
@Composable
fun TermsandConditionsScreen() {
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)
        .padding(20.dp)) {
            LargeTextComponents(value = stringResource(R.string.terms_and_conditions))
    }
    SystemBackButtonHandler {
        AppRouter.navigate(Screen.signUpScreen)
    }
}