package com.example.authapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.authapp.R
import com.example.authapp.components.ButtonComponent
import com.example.authapp.components.CheckBoxComponent
import com.example.authapp.components.LargeTextComponents
import com.example.authapp.components.NormalTextComponents
import com.example.authapp.components.PasswordTextField
import com.example.authapp.components.TextField
import com.example.authapp.navigation.AppRouter
import com.example.authapp.navigation.Screen

@Preview(showBackground = true)
@Composable
fun SignUpScreen() {
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
    ) {
        Column {
            Row {
                NormalTextComponents(value = stringResource(id = R.string.heyThere))
            }
            Row {
                LargeTextComponents(value = stringResource(id = R.string.createAccount))
            }
            Spacer(modifier = Modifier.padding(20.dp))
            Row {
                TextField(labelValue = "First Name", painter = painterResource(id = R.drawable.user))
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Row {
                TextField(labelValue = "Last Name", painter = painterResource(id = R.drawable.user))
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Row {
                TextField(labelValue = "Email", painter = painterResource(id = R.drawable.email))
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Row {
                PasswordTextField(labelValue = "Password", painter = painterResource(id = R.drawable.lock ))
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Row {
                CheckBoxComponent(value = stringResource(id = R.string.TandC),
                    onTextSelected = {
                        AppRouter.navigate(Screen.TermsandConditionsScreen)
                    })
            }
            Spacer(modifier = Modifier.padding(30.dp))
            Row {
                ButtonComponent(value = "Register")
            }
        }
    }
}