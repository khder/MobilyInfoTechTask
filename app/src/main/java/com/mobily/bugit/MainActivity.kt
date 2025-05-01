package com.mobily.bugit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobily.bugit.ui.Screens
import com.mobily.bugit.ui.addBug.AddBugScreen
import com.mobily.bugit.ui.home.HomeScreen
import com.mobily.bugit.ui.splash.SplashScreen
import com.mobily.bugit.ui.theme.BugItTheme
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BugItTheme {
                NavigationStack(
                    navController = rememberNavController()
                )
            }
        }
    }
}

@Composable
fun NavigationStack(
    navController:NavHostController
) {

    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {

        composable(route = Screens.SplashScreen.route) {
            SplashScreen(navHostController = navController)
        }

        composable(route = Screens.HomeScreens.route) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screens.AddBugScreens.route,
        ) {
            AddBugScreen(navHostController = navController)
        }
    }
}