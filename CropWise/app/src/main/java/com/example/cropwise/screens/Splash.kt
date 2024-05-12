package com.example.cropwise.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.cropwise.R
import com.example.cropwise.ViewModel.AuthViewModel
import com.example.cropwise.navigation.Route
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun Splash(navController: NavHostController){
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val (image) = createRefs()

        Image(painter = painterResource(id = R.drawable.darklogo),
            contentDescription = "logo", modifier = Modifier.constrainAs(image){
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }.size(1000.dp) )
    }
    LaunchedEffect(key1 = true) {

        delay(500)
        if(FirebaseAuth.getInstance().currentUser!= null) {
            navController.navigate(Route.BottomNav.routes){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
        else{
            navController.navigate(Route.Register.routes){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }

    }
}