package com.example.ticketyours.data.model

import kotlinx.serialization.Serializable


@Serializable
data class Division(
    val name: String,
    val rows: Int
)

@Serializable
data class Seat(
    val division: String,
    val row: Int,
    val column: Int,
    var isMarked: Boolean = false
)

