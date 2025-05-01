package com.mobily.bugit.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mobily.bugit.R
import com.mobily.bugit.ui.Screens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navHostController: NavHostController){
    LaunchedEffect(Unit) {
        delay(3000)
        navHostController.navigate(Screens.HomeScreens.route) {
            popUpTo(Screens.SplashScreen.route) {
                inclusive = true
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment= Alignment.Center,
    ){
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.bug),
            contentDescription = "LauncherIcon"
        )
    }
}