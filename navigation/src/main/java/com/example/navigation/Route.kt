package com.example.navigation

sealed class Route(val routeLink : String) {
    data class NavigateDeeplinkRoute(val deeplinkNav : DeeplinkNavigator ) : Route(deeplinkNav.routeLink)
}