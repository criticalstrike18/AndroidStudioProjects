package com.example.threadsclone.navigation

sealed class Route(val routes:String) {

    object Home : Route("home")
    object AddThread : Route("addThread")
    object BottomNav : Route("bottomNav")
    object Notification : Route("notification")
    object Search : Route("search")
    object Profile : Route("profile")
    object Splash : Route("splash")
    object Login : Route("login")
    object Register : Route("register")
}