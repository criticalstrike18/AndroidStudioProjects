package com.example.videoapp.ViewModel

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoapp.data.Video
import com.example.videoapp.utils.Supabaseclient.client
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class SupabaseviewModel : ViewModel() {
    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> = _videos.asStateFlow() // Make it immutable outside the ViewModel

    init {
        viewModelScope.launch {
            _videos.value = getVideoList() // Initialize with fetched data (or empty list on error)
        }
    }
    private fun generateUniqueName(): String {
        return UUID.randomUUID().toString()
    }
    fun upload(
        videoUri: Uri,
        extensionVideo: String,
        thumbnailUri: Uri,
        extensionThumbnail: String,
        title: String,
        description: String,
        contentResolver: ContentResolver,
        context: Context
    ) {
        val name = generateUniqueName()
        Log.d("Name", extensionVideo)
        val filePathVideo = "videoUploads/${name}.${extensionVideo}"
        val filePathThumbnail = "thumbnailUploads/${name}.${extensionThumbnail}"
        viewModelScope.launch {
            try {
                val videoBytes = contentResolver.openInputStream(videoUri)!!.use { it.readBytes() }
                val thumbnailBytes = contentResolver.openInputStream(thumbnailUri)!!.use { it.readBytes() }
                client.storage
                    .from("Uploads")
                    .upload(filePathVideo, videoBytes,true)
                client.storage
                    .from("Uploads")
                    .upload(filePathThumbnail, thumbnailBytes,true)
                Toast.makeText(context,"Video Uploaded", Toast.LENGTH_SHORT).show()

            }catch (e: Exception) {
                Toast.makeText(context, "failed to Upload", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }

        }
        viewModelScope.launch{
            val videoUrl = client.storage["Uploads"].publicUrl(filePathVideo)
            val thumbnailUrl = client.storage["Uploads"].publicUrl(filePathThumbnail)
            Log.d("VideoUrl", videoUrl)
            try {
                client.postgrest
                    .from("videoDetails")
                    .insert(Video(title = title, description = description, videoUrl = videoUrl, thumbnailUrl = thumbnailUrl))
                Log.d("DB","DB Updated")
            }catch (e: Exception) {
                Toast.makeText(context, "failed to Upload", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
        // Your upload logic using Supabase Storage goes here
    }
    private suspend fun getVideoList(): List<Video> {
                val response = client.postgrest
                    .from("videoDetails")
                    .select()
                    .decodeList<Video>()
        Log.d("VideoList", response.toString())
        return response
    }

    fun updateVideos(query: String) {
        viewModelScope.launch {
            _videos.value = if (query.isEmpty()) {
                getVideoList() // Get all videos if query is empty
            } else {
                searchVideos(query) // Get filtered videos
            }
        }
    }
    private suspend fun searchVideos(query: String): List<Video> {
        val response = client.postgrest
            .from("videoDetails") // Replace with your actual table name
            .select{
                filter {
                    or {
                        ilike("title", "%$query%")
                        ilike("description", "%$query%")
                    }
                }
            }
            .decodeList<Video>()
        Log.d("SearchList", response.toString())
        return response
    }


}