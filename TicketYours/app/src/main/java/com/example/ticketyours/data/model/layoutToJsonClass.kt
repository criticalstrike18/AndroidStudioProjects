package com.example.ticketyours.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LayoutData(
    val divisions: List<DivisionData>,
    val maxSeatsPerRow: Int
)

@Serializable
data class DivisionData(
    val name: String,
    val rows: List<RowData>
)

@Serializable
data class RowData(
    val seats: List<SeatData>
)

@Serializable
data class SeatData(
    val division: String,
    val row: Int,
    val column: Int,
    val isMarked: Boolean
)
