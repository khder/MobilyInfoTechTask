package com.mobily.bugit.ui

sealed class Screens(val route:String) {
    data object SplashScreen : Screens("SplashScreen")
    data object HomeScreens: Screens("HomeScreen")
    data object AddBugScreens : Screens("AddBugScreen")
}