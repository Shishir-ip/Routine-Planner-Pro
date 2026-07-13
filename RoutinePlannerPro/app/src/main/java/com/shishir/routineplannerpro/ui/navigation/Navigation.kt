package com.shishir.routineplannerpro.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object DailyRoutine : Screen("daily_routine")
    object ClassRoutine : Screen("class_routine")
    object CustomRoutine : Screen("custom_routine/{routineId}") {
        fun createRoute(routineId: String) = "custom_routine/$routineId"
    }
    object Settings : Screen("settings")
    object AddActivity : Screen("add_activity/{routineId}/{routineType}") {
        fun createRoute(routineId: String, routineType: String) = "add_activity/$routineId/$routineType"
    }
    object ApiKeySettings : Screen("api_key_settings")
}
