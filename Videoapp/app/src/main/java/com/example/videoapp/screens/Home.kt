package com.example.videoapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.videoapp.components.LargeTextComponent
import com.example.videoapp.components.UploadDialog
import com.example.videoapp.components.UploadFab
import com.example.videoapp.components.VideoCard
import com.example.videoapp.components.VideoPlayerDialog
import com.example.videoapp.ViewModel.SupabaseviewModel
import com.example.videoapp.data.Video

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val viewModel = SupabaseviewModel()
    var showUploadDialog by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var selectedVideo by remember { mutableStateOf<Video?>(null) }
    var showVideoDialog by remember { mutableStateOf(false) }
    val videos by viewModel.videos.collectAsState()
    Column {
        LargeTextComponent(value = "AnyFlick")
        Spacer(modifier = Modifier.height(8.dp))
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { },
                    modifier = Modifier.padding(bottom = 12.dp),
                    navigationIcon = {},
                    actions = {
                        SearchBar(
                            query = searchText,
                            onQueryChange = { newQuery ->
                                searchText = newQuery
                                viewModel.updateVideos(newQuery)
                            },
                            onSearch = {
                                viewModel.updateVideos(searchText)
                            },
                            active = false,
                            onActiveChange = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            placeholder = { Text("Search") },
                            leadingIcon = {  // Add leadingIcon composable
                                Icon(
                                    Icons.Filled.Search,
                                    contentDescription = "Search",
                                    tint = contentColorFor(backgroundColor = MaterialTheme.colorScheme.surface)
                                )
                            }

                        ) {} // Empty content lambda
                    },
                     // Add scroll behavior
                )
            },
            floatingActionButton = {
                UploadFab(onClick = { showUploadDialog = true })
                Spacer(modifier = Modifier.padding(40.dp))
            }
        ) { innerPadding -> // Use 'innerPadding' here
            LazyColumn(
                 // Apply padding from Scaffold
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) { // Adjust padding as needed
                items(videos.size) { index ->
                    VideoCard(video = videos[index]) { clickedVideo ->
                        // Navigate to video details or player screen with clickedVideo.videoUrl
                        selectedVideo = clickedVideo
                        showVideoDialog = true
                    }
                }
            }
        }
    }
    if (showVideoDialog) {
        VideoPlayerDialog(video = selectedVideo!!, onDismiss = { showVideoDialog = false })
    }
    if (showUploadDialog) {
        UploadDialog(
            onDismiss = { showUploadDialog = false }
        )
    }
}

