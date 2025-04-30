package com.mobily.bugit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobily.bugit.ui.Screen
import com.mobily.bugit.ui.addBug.AddBugScreen
import com.mobily.bugit.ui.home.HomeScreen
import com.mobily.bugit.ui.theme.BugItTheme
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BugItTheme {
                var title by remember {
                    mutableStateOf("")
                }
                var isShowBackButton by remember {
                    mutableStateOf(false)
                }
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = title)
                            },
                            navigationIcon = {
                                if (isShowBackButton) {
                                    IconButton(onClick = {
                                        navController.navigateUp()
                                    }) {
                                        Icon(
                                            imageVector = Icons.Filled.ArrowBack,
                                            contentDescription = "Share items"
                                        )
                                    }
                                }
                            }
                        )
                    }
                ) { _ ->
                    NavigationStack(
                       onTitleChangeListener = {outTitle->
                           title = outTitle
                       }  ,
                        onBackButtonChangeListener = {outIsShowBackButton->
                            isShowBackButton = outIsShowBackButton
                        },
                        navController = rememberNavController()
                    )
                }
            }
        }
    }
}

@Composable
fun NavigationStack(
    onTitleChangeListener:(String)->Unit,
    onBackButtonChangeListener:(Boolean)->Unit,
    navController:NavHostController
) {

    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            onTitleChangeListener.invoke("HomeScreen")
            onBackButtonChangeListener.invoke(false)
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.AddBugScreen.route,
        ) {
            onTitleChangeListener.invoke("AddBugScreen")
            onBackButtonChangeListener.invoke(true)
            AddBugScreen()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BugItTheme {
        Greeting("Android")
    }
}