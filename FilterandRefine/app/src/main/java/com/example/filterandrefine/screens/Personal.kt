package com.example.filterandrefine.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Sort
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.filterandrefine.components.ProfileCard
import com.example.filterandrefine.components.UploadFab
import com.example.filterandrefine.data.ProfileData
import com.example.filterandrefine.navigation.ScreenRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalScreen(navController: NavHostController){
    var searchText by remember { mutableStateOf("") }
    var showUploadDialog by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    Column(modifier = Modifier.fillMaxSize()) {
        val data1 = ProfileData(
            "SP", name = "Saksham Patel","Pune","Android Developer","100 m",50,"Lorem Ipsum"
        )
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Search Bar and Filter ")},
                    modifier = Modifier.padding(bottom = 8.dp),
                    actions = {
                        SearchBar(
                            query = searchText,
                            onQueryChange = {  newQuery ->
                                searchText = newQuery  },
                            onSearch = {     },
                            active = false,
                            onActiveChange = {},
                            modifier = Modifier
                                .fillMaxWidth(.8f)
                                .padding(start = 16.dp, bottom = 10.dp),
                            placeholder = { Text("Search",
                                fontSize = 12.sp)},
                            leadingIcon = {  // Add leadingIcon composable
                                Icon(
                                    Icons.Filled.Search,
                                    contentDescription = "Search",
                                    tint = contentColorFor(backgroundColor = MaterialTheme.colorScheme.surface)
                                )
                            }

                        ) {}
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(end = 20.dp, bottom = 8.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Box(Modifier.clickable(
                                onClick = { navController.navigate(ScreenRoute.FilterScreen.route){
                                    launchSingleTop = true
                                } },
                                indication = rememberRipple(
                                    bounded = false,
                                    radius = 25.dp
                                ),
                                interactionSource = interactionSource
                            ),) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Outlined.Sort, contentDescription = "Filter",
                                    modifier = Modifier
                                        .size(32.dp)
                                )
                            }

                        }
                    },
                )
            },
            floatingActionButton = {
                UploadFab(onClick = { showUploadDialog = true })
                Spacer(modifier = Modifier.padding(vertical = 70.dp, horizontal = 32.dp))
            }
        ) { innerPadding -> // Use 'innerPadding' here
            LazyColumn(
                // Apply padding from Scaffold
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) { // Adjust padding as needed
                items(5) { index ->
                    ProfileCard(data1)
                }
                item {
                    Spacer(modifier = Modifier.padding(45.dp))
                }
            }
        }
    }
}