package com.example.videoapp.components

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.example.videoapp.R
import com.example.videoapp.ViewModel.SupabaseviewModel
import com.example.videoapp.data.Video


@Composable
fun LargeTextComponent(value:String) {
    Text(text = value,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 0.dp),
        fontSize = 45.sp,
        fontFamily = FontFamily(Font(R.font.poetsenone_regular)),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.secondary
    )
}

@Composable
fun UploadFab(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        text = { Text("Upload Video") },
        icon = { Icon(Icons.Filled.Add, contentDescription = "Upload") },
        onClick = onClick,
    )
}

@Composable
fun UploadDialog(onDismiss: () -> Unit) {
    val viewModel = SupabaseviewModel()
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedVideoUri by remember { mutableStateOf<Uri?>(null) }
    var selectedThumbnailUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val contentResolver = context.contentResolver
    fun checkVideoSize(uri: Uri): Boolean {
        contentResolver.openFileDescriptor(uri, "r")?.use { pfd -> // Use 'use' block
            val fileSizeInBytes = pfd.statSize
            val fileSizeInMB = fileSizeInBytes / (1024 * 1024)
            return fileSizeInMB <= 50
        } ?: return false
    }
    fun checkThumbnailSize(uri: Uri): Boolean {
        contentResolver.openFileDescriptor(uri, "r")?.use { pfd -> // Use 'use' block
            val fileSizeInBytes = pfd.statSize
            val fileSizeInMB = fileSizeInBytes / (1024 * 1024)
            return fileSizeInMB <= 15
        } ?: return false
    }
    fun getFileName(context: Context, uri: Uri): String? {
        // Check if the Uri scheme is content
        if (uri.scheme.equals("content")) {
            // If the Uri is from the MediaStore, get the DISPLAY_NAME column
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (cursor.moveToFirst()) {
                    return cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
                }
            }
        }
        // If not from MediaStore, return the path segment after the last slash
        return uri.path?.lastIndexOf('/')?.plus(1)?.let { uri.path?.substring(it) }
    }
    fun getFileExtension(context: Context, uri: Uri): String? {
        return if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            val mimeType = context.contentResolver.getType(uri)
            MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) // Extract extension from MIME type
        } else {
            MimeTypeMap.getFileExtensionFromUrl(uri.toString()) // Directly get extension from URL
        }
    }
    val startActivityForVideo = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
           val tempSelectedVideo = result.data?.data ?: return@rememberLauncherForActivityResult
            if (checkVideoSize(tempSelectedVideo)) {
                selectedVideoUri = tempSelectedVideo
            }else{
                Toast.makeText(context, "File Size must be less than 50MB", Toast.LENGTH_SHORT).show()
            }
        }
    }
    val startActivityForThumbnail = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val tempSelectedImage = result.data?.data ?: return@rememberLauncherForActivityResult
            if (checkThumbnailSize(tempSelectedImage)) {
                selectedThumbnailUri = tempSelectedImage
            }else{
                Toast.makeText(context, "File Size must be less than 15MB", Toast.LENGTH_SHORT).show()
            }
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        title = { Text("Upload Video") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") }
                )
                Spacer(modifier = Modifier.padding(8.dp))
                if(selectedVideoUri == null)
                    Button(onClick = {
                        val intent = Intent(Intent.ACTION_GET_CONTENT)
                        intent.type = "video/*"
                        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
                        startActivityForVideo.launch(intent)
                    }) {
                        Text("Select Video")
                    }
                else
                    Text(getFileName(context, selectedVideoUri!!).toString().trim())
                Spacer(modifier = Modifier.padding(8.dp))
                if (selectedThumbnailUri == null)
                    Button(onClick = {
                        val intent = Intent(Intent.ACTION_GET_CONTENT)
                        intent.type = "image/*"
                        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
                        startActivityForThumbnail.launch(intent)
                    }) {
                        Text("Select Thumbnail")
                    }
                else

                    Text(getFileName(context, selectedThumbnailUri!!).toString().trim())
            }
        },
        confirmButton = {
            Button(onClick = {
                if (title.isNotEmpty() && selectedVideoUri != null && selectedThumbnailUri != null) {
                    viewModel.upload(selectedVideoUri!!,getFileExtension(context, selectedVideoUri!!)!!,selectedThumbnailUri!!,getFileExtension(context, selectedThumbnailUri!!)!! ,title, description,contentResolver,context)
                    onDismiss() // Close the dialog after upload
                }
            }) {
                Text("Upload")
            }
        }
    )
}

@Composable
fun VideoCard(video: Video, onVideoClick: (Video) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onVideoClick(video) }, // Click listener
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box( // Add Box for overlay
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp)), // Optional: Rounded corners
                contentAlignment = Alignment.Center // Center the PlayCircleOutline
            ) {
                AsyncImage( // Load thumbnail image (place below Icon)
                    model = video.thumbnailUrl,
                    contentDescription = video.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Icon(
                    imageVector = Icons.Filled.PlayCircleOutline,
                    contentDescription = "Play Button",
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = video.title,
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = video.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerDialog(video: Video, onDismiss: () -> Unit) {
    val context = LocalContext.current
    val exoPlayer = remember(video.videoUrl) { // Keyed to videoUrl for proper updates
        ExoPlayer.Builder(context).build()
    }

    // Attach player and clean up on dispose
    DisposableEffect(key1 = exoPlayer) {
        onDispose { exoPlayer.release() }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false) // Full-width dialog
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column {
                // Video Player
                Box(modifier = Modifier.fillMaxWidth().height(750.dp)) { // Adjust height as needed
                    AndroidView(
                        factory = { context ->
                            PlayerView(context).apply {
                                player = exoPlayer
                                useController = true
                                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // Video Details (optional)
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(video.title, style = MaterialTheme.typography.headlineSmall)
                    Text(video.description, style = MaterialTheme.typography.bodyMedium)
                    // ... (Other details as needed)
                }
            }
        }

        // Lifecycle handling for ExoPlayer
        LaunchedEffect(video.videoUrl) {
            val mediaItem = MediaItem.fromUri(video.videoUrl)
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
        }
    }
}