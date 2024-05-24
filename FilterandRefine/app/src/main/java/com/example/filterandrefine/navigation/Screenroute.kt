package com.example.filterandrefine.navigation

sealed class ScreenRoute(val route: String) {
    data object ExploreScreen : ScreenRoute("explore_screen")
    data object PersonalScreen : ScreenRoute("personal_screen")
    data object ServicesScreen : ScreenRoute("services_screen")
    data object BusinessesScreen : ScreenRoute("businesses_screen")
    data object RefineScreen : ScreenRoute("refine_screen")
    data object ConnectionsScreen : ScreenRoute("connections_screen")
    data object ChatScreen : ScreenRoute("chat_screen")
    data object ContactsScreen : ScreenRoute("contacts_screen")
    data object GroupsScreen : ScreenRoute("groups_screen")
}