package com.mobily.bugit.ui

sealed class Screen(val route:String) {
    data object HomeScreen: Screen("HomeScreen")
    data object AddBugScreen : Screen("AddBugScreen")
}