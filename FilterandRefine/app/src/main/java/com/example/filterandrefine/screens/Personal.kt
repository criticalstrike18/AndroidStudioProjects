package com.example.filterandrefine.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.filterandrefine.R
import com.example.filterandrefine.components.CustomSearchBar
import com.example.filterandrefine.components.ProfileCard
import com.example.filterandrefine.data.ProfileData
import com.example.filterandrefine.navigation.ScreenRoute

@Composable
fun PersonalScreen(navController: NavHostController){
    var searchText by remember { mutableStateOf("") }
    var showUploadDialog by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = colorResource(id = R.color.white))) {
        val data1 = ProfileData(
            1,"now","SP", "Saksham Patel","Pune","Android Developer",100,50,"Lorem Ipsum"
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically){
            CustomSearchBar(
                searchText = searchText,
                onQueryChange = { newQuery ->
                    searchText = newQuery
                },
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .padding(start = 16.dp),
                placeholderText = "Search...",
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp, bottom = 8.dp),
                horizontalAlignment = Alignment.End
            ) {
                Box(
                    Modifier.clickable(
                        onClick = {
                            navController.navigate(ScreenRoute.FilterScreen.route) {
                                launchSingleTop = true
                            }
                        },
                        indication = rememberRipple(
                            bounded = false,
                            radius = 25.dp
                        ),
                        interactionSource = interactionSource
                    ),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.Sort,
                        contentDescription = "Filter",
                        modifier = Modifier
                            .size(32.dp)
                    )
                }

            }
        }
        Spacer(modifier = Modifier.padding(4.dp))
        Box {
            LazyColumn(
                // Apply padding from Scaffold
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 4.dp)
            ) { // Adjust padding as needed
                items(5) { index ->
                    ProfileCard(data1)
                }
                item {
                    Spacer(modifier = Modifier.padding(45.dp))
                }
            }
            FloatingActionButton(
                onClick = { showUploadDialog = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 110.dp, end = 25.dp),
                shape = CircleShape,
                containerColor = colorResource(id = R.color.darkBlue)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Upload", tint = colorResource(id = R.color.white))
            }

        }
    }
}