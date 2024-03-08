package com.example.authapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.authapp.R
import com.example.authapp.components.LargeTextComponents
import com.example.authapp.components.NormalTextComponents

@Preview(showBackground = true)
@Composable
fun LogInScreen() {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Row {
                NormalTextComponents(value = stringResource(id = R.string.heyThere))
            }
            Row {
                LargeTextComponents(value = stringResource(id = R.string.welcomeBack))
            }
        }
    }
}