package com.example.ticketyours.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ticketyours.presentation.components.DOBTextField
import com.example.ticketyours.presentation.components.FilledButton
import com.example.ticketyours.presentation.components.NormalTextField
import com.example.ticketyours.presentation.components.TitleText
import com.example.ticketyours.presentation.components.VerifiableTextField
import com.example.ticketyours.presentation.navigation.Route

@Composable
fun AuthenticationPage(
    navController: NavController
){
    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)) {
        TitleText(
            "Register with Us",
            modifier = Modifier.padding(18.dp),
        )
        NormalTextField(
            value = firstname,
            labelValue = "First Name",
            keyboardType = KeyboardType.Text,
            onValueChange = { firstname = it })
        NormalTextField(
            value = lastname,
            labelValue = "Last Name",
            keyboardType = KeyboardType.Text,
            onValueChange = { lastname = it })
        VerifiableTextField(
            value = phoneNumber,
            labelValue = "Phone Number",
            keyboardType = KeyboardType.Number,
            onValueChange = { phoneNumber = it },
        )
        VerifiableTextField(
            value = email,
            labelValue = "Email",
            keyboardType = KeyboardType.Email,
            onValueChange = {email = it}
        )
        DOBTextField(
            value = dob,
            onValueChange = { dob = it }
        )
        Spacer(modifier = Modifier.padding(16.dp))
        FilledButton(text = "Continue",
            onClick = {
                if(firstname.isNotEmpty() && lastname.isNotEmpty()
                    && phoneNumber.isNotEmpty() && email.isNotEmpty() && dob.isNotEmpty()){
                    navController.navigate(Route.SetPasswordPage
                        (firstname,lastname,phoneNumber,email,dob))
                }
                else{
                    //TODO: Show error
                }

            }
        )

//        END

//        Spacer(modifier = Modifier.padding(8.dp))
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            HorizontalDivider(
//                modifier = Modifier
//                    .weight(1f)
//                    .height(2.dp),
//                color = Color.Gray.copy(alpha = 0.5f)
//            )
//            Text(
//                text = "OR",
//                modifier = Modifier.padding(horizontal = 16.dp),
//                style = MaterialTheme.typography.bodyMedium,
//                color = Color.DarkGray
//            )
//            HorizontalDivider(
//                modifier = Modifier
//                    .weight(1f)
//                    .height(2.dp),
//                color = Color.Gray.copy(alpha = 0.5f)
//            )
//        }
//        Spacer(modifier = Modifier.padding(8.dp))
//        SeparateSingleText(
//            initialText = "Already With Us ? ",
//            highlightText = "Log In",
//            onResendCode = { /*TODO*/ },
//            annotation = "LOGIN"
//        )
    }
}