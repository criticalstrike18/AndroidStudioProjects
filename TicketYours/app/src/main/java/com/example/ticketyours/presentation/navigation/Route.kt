package com.example.ticketyours.presentation.navigation

import com.example.ticketyours.data.model.Division
import kotlinx.serialization.Serializable

sealed class Route {
    @Serializable
    data object AuthenticationPage : Route()
//    @Serializable
//    data object LoginPage : Route()
    @Serializable
    data class SetPasswordPage(
        val firstName: String,
        val lastName: String,
        val phoneNumber: String,
        val email: String,
        val dob: String
    ) : Route()
    @Serializable
    data object TheaterRegistrationPage : Route()
    @Serializable
    data class ScreenDetailsPage(
        val currentScreenNo: Int,
        val totalScreens: Int
    ) : Route()

    @Serializable
    data class ScreenDivisionDetailsPage(
        val currentScreenNo: Int,
        val totalScreens: Int,
        val maxSeatsPerRow: Int,
        val currentDivisionNo: Int
    ) : Route()
    @Serializable
    data class ScreenSeatLayoutEditor(
        val currentScreenNo: Int,
        val totalScreens: Int,
        val maxSeatsPerRow: Int,
    ) : Route()
    @Serializable
    data object ViewSeatLayoutScreen : Route()
}