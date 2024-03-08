package com.example.threadsclone.components

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.util.Patterns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.rounded.MailOutline
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.threadsclone.R
import com.example.threadsclone.ViewModel.AuthViewModel
import com.example.threadsclone.navigation.Route
import com.google.firebase.auth.FirebaseUser


//Field Components

@Composable
fun TextValue(labelValue: String,textValue: String, onValueChange: (String) -> Unit,  isError: Boolean = false){
    var isFocused by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = textValue,
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .onFocusChanged { isFocused = it.isFocused },
        onValueChange = onValueChange,
        label = { Text(text = labelValue) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ), singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Rounded.Person, contentDescription = "Email Icon")
        },
        isError = isError && isFocused
        )

}

fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Composable
fun EmailValue(labelValue: String,email: String, onValueChange: (String) -> Unit,  isError: Boolean = false){
    var isFocused by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = email,
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .onFocusChanged {
                isFocused = it.isFocused
                if (it.isFocused) { // Check validity when focus is lost
                    showError = !isValidEmail(email)
                }
            },
        onValueChange = onValueChange,
        label = { Text(text = labelValue) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ), singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Rounded.MailOutline, contentDescription = "Email Icon")
        },
        isError = isError && showError
    )
}

@Composable
fun PasswordValue(labelValue: String, password: String, onValueChange: (String) -> Unit,  isError: Boolean = false){
    var isFocused by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = password,
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .onFocusChanged { isFocused = it.isFocused },
        onValueChange = onValueChange,
        label = { Text(text = labelValue) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
//            imeAction = ImeAction.Next
        ), singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Outlined.Lock, contentDescription = "Email Icon")
        },
        isError = isError && isFocused
    )
}

@Composable
fun LoginButton(email: String, password: String){
    val authViewModel : AuthViewModel = viewModel()
    ElevatedButton(onClick = {
        val isValid = email.isNotBlank() && password.isNotBlank()
        if(isValid){
            Log.d("valid","It is valid")
            authViewModel.login(email,password)
        }
        else{
            Log.d("not valid","It is not valid")
        }
    }) {
        Text(text = "Login",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            ), modifier = Modifier.fillMaxWidth(0.3f)
        )
    }
}

@Composable
fun RegisterButton(firstName: String, lastName: String, email: String, password: String){
    val authViewModel : AuthViewModel = viewModel()
    val context = LocalContext.current
    var permissionGranted by remember { mutableStateOf(false) }
    var showExplanation by remember { mutableStateOf(false) }
    if (showExplanation) {
        Text("Internet permission is required for registration.")
    }
    var isValid by remember { mutableStateOf(false) }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            permissionGranted = isGranted
            if (!isGranted) {
                showExplanation = true
            }
        }
    )

    ElevatedButton(onClick = {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET)
            == PackageManager.PERMISSION_GRANTED) {
            isValid = firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank() && password.isNotBlank()
            if(isValid){
                Log.d("valid","It is valid")
                authViewModel.register(email,password,firstName,lastName, context )
            }
            else{
                Log.d("not valid","It is not valid")
            }
        }
        else {
            requestPermissionLauncher.launch(Manifest.permission.INTERNET)
        }
    }

    ) {
        Text(text = "Register",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            ), modifier = Modifier.fillMaxWidth(0.3f)
        )
    }
}

@Composable
fun HandleRegistrationSuccess(firebaseUser: FirebaseUser?, navController: NavHostController) {
    LaunchedEffect(key1 = firebaseUser) {
        if (firebaseUser != null) {
            Log.d("navigating", "User registered")
            navController.navigate(Route.BottomNav.routes) {
                launchSingleTop = true
            }
        } else {
            Log.d("failed", "Registration failed")
        }
    }
}
//Text Components
@Composable
fun LargeTextComponents(value:String) {
    Text(text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 0.dp)
            .padding(top = 1.dp, bottom = 30.dp),
        fontSize = 45.sp,
        fontFamily = FontFamily(Font(R.font.lobstertwo_regular)),
        textAlign = TextAlign.Center,
        color = Color.White

    )
}

//@Composable
//fun SignUpTextButton(){
//    TextButton(onClick = { /*TODO*/ }) {
//        Text(text = "SignUp",
//            style = TextStyle(
//                fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
//                fontSize = 12.sp,
//                textAlign = TextAlign.Center,
//            ),
//        )
//    }
//}

@Composable
fun ClickableLoginTextComponent(navController : NavHostController){
    val signup = stringResource(id = R.string.signup)

    val annotatedString = buildAnnotatedString {
        withStyle(SpanStyle(fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
            fontSize = 12.sp, color = Color.White)){
            pushStringAnnotation(tag = "Initial-text", annotation = "Initial-text")
            append("Don't have an Account?")}
        withStyle(SpanStyle(fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
            fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)){
            pushStringAnnotation(tag = signup, annotation = "SignUp")
            append(signup)
        }
    }
    ClickableText(text = annotatedString , onClick = {
            offset -> annotatedString.getStringAnnotations(offset,offset)
        .firstOrNull()?.also {
                span ->  if (span.item == "SignUp") {
            navController.navigate(Route.Register.routes){
                launchSingleTop = true
            }
        }
        }
    })
}

@Composable
fun ClickableRegisterTextComponent(navController : NavHostController){
    val login = stringResource(R.string.log_in)

    val annotatedString = buildAnnotatedString {
        withStyle(SpanStyle(fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
            fontSize = 12.sp, color = Color.White)){
            pushStringAnnotation(tag = "Initial-text", annotation = "Initial-text")
            append("Already have an Account?")}
        withStyle(SpanStyle(fontFamily = FontFamily(Font(R.font.robotocondensed_regular)),
            fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)){
            pushStringAnnotation(tag = login, annotation = "LogIn")
            append(login)
        }
    }
    ClickableText(text = annotatedString , onClick = {
            offset -> annotatedString.getStringAnnotations(offset,offset)
        .firstOrNull()?.also {
                span ->  Log.d("ClickableTextComponent","{${span.item}}")
            if (span.item == "LogIn") {
            navController.navigate(Route.Login.routes){
                launchSingleTop = true
            }
        }
        }
    })
}