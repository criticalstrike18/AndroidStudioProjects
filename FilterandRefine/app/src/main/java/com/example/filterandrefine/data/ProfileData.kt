package com.example.filterandrefine.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileData(
    val id: Int? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    val initials: String,
    val name: String,
    val location: String,
    val designation: String,
    val distance: Int,
    val profileScore: Int,
    val bio: String
)
