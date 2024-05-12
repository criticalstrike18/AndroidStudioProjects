package com.example.videoapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Video(
    val id: Int? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    val title: String,
    val description: String,
    val videoUrl: String,
    val thumbnailUrl: String,
)
