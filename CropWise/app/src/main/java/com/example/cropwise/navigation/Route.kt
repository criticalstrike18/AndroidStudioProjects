package com.example.cropwise.navigation

sealed class Route(val routes:String) {

    object Home : Route("home")
    object BottomNav : Route("bottomNav")
    object Splash : Route("splash")
    object CropRecommendations : Route("CropRecommendations")
    object Login : Route("login")
    object Register : Route("register")
    object ParamsUpdater : Route("parmsupdater")
    object PricePrediction : Route("PricePrediction")
    object Logout : Route("Logout")
}
