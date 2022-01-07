package com.gerhardrvv.litit.ui.navigation

enum class LitItScreens() {
    Home;

    companion object {
        fun fromRoute(route: String?): LitItScreens =
            when (route?.substringBefore("/")) {
                Home.name -> Home
                null -> Home
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}
