package com.example.cropwise.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cropwise.R
import com.example.cropwise.ViewModel.AuthViewModel
import com.example.cropwise.components.ClickableRegisterTextComponent
import com.example.cropwise.components.EmailValue
import com.example.cropwise.components.HandleRegistrationSuccess
import com.example.cropwise.components.LargeTextComponents
import com.example.cropwise.components.PasswordValue
import com.example.cropwise.components.RegisterButton
import com.example.cropwise.components.TextValue

@Composable
fun Register(navController: NavHostController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)

    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Row {
            LargeTextComponents("Sign Up")
        }
        Spacer(modifier = Modifier.padding(5.dp))
        Row {
            TextValue(labelValue = stringResource(id = R.string.first_name),
                textValue = firstName,
                onValueChange = {firstName = it},
                isError = firstName.isBlank()
            )
        }
        Spacer(modifier = Modifier.padding(5.dp))
        Row {
            TextValue(labelValue = stringResource(id = R.string.last_name),
                textValue = lastName,
                onValueChange = {lastName = it},
                isError = lastName.isBlank()
            )
        }
        Spacer(modifier = Modifier.padding(5.dp))
        Row {
            EmailValue(
                stringResource(R.string.email),
                email = email,
                onValueChange = {email = it},
                isError = email.isBlank()
            )
        }
        Spacer(modifier = Modifier.padding(5.dp))
        Row {
            PasswordValue(
                stringResource(R.string.password),
                password = password,
                onValueChange = {password = it},
                isError = password.isBlank())
        }
        Spacer(modifier = Modifier.padding(18.dp))
        Row {
            RegisterButton(firstName = firstName,
                lastName = lastName,
                email = email,
                password = password)
            HandleRegistrationSuccess(firebaseUser, navController)
        }
        Spacer(modifier = Modifier.padding(18.dp))
        Row {
            ClickableRegisterTextComponent(navController)
        }


    }
}