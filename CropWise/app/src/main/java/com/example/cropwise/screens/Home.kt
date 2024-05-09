package com.example.cropwise.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRightAlt
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cropwise.R
import com.example.cropwise.ViewModel.AuthViewModel
import com.example.cropwise.navigation.Route


@Composable
fun Home(navController: NavHostController) {
    val viewModel = viewModel<AuthViewModel>()
    viewModel.fetchusername()
    val name by viewModel.userFirstName.observeAsState("")
    Column(modifier = Modifier
        .padding(12.dp)
        .fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth(), // Make Row occupy full width
            horizontalArrangement = Arrangement.SpaceBetween // Distribute items with space in between
        ) {
            Text(
                text = "Hi $name ",
                fontFamily = FontFamily(Font(R.font.robotocondensed_semibold)),
                fontSize = 35.sp,
                modifier = Modifier.padding(2.dp)
            )
            IconButton(onClick = {
                // Example functionality: Log out the user
                navController.navigate(Route.Logout.routes)
            }) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close",
                    modifier = Modifier.fillMaxSize()

                )
            }
        }
        Divider()
        HomeCard("Get Crop Recommendation", onClick = {navController.navigate(Route.CropRecommendations.routes){
            launchSingleTop = true
        } })
        HomeCard(value = "Update Soil Parameters",onClick = {navController.navigate(Route.ParamsUpdater.routes)})
        HomeCard(value = "Price Prediction",onClick = {navController.navigate(Route.PricePrediction.routes)})
//        HomeCard(value = "Fertilizer Recommendation",onClick = {navController.navigate(Route.CropRecommendations.routes)})

    }
//    Box2(crop = "Muskmelon")
}

@Composable
fun HomeCard(value: String,onClick: () -> Unit,) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(10.dp, top = 16.dp)
            .clickable { onClick() },
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = value,
                modifier = Modifier
                    .padding(16.dp),
            )
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowRightAlt, contentDescription = "Arrow",
                modifier = Modifier
                    .size(55.dp)
                    .padding(8.dp)
                )
        }
    }
}
