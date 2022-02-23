package com.example.cyclistance.feature_readable_displays.presentation.splash_screen.components

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.cyclistance.R
import com.example.cyclistance.feature_authentication.presentation.AuthenticationScreen
import com.example.cyclistance.feature_authentication.presentation.theme.BackgroundColor
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember{ Animatable(initialValue = 0f) }
    LaunchedEffect(key1 = true){
        scale.animateTo(0.9f,
            animationSpec = tween(
                durationMillis = 500,
                easing =  {  OvershootInterpolator(1.2f).getInterpolation(it) }
            ))
        delay(1200L)

        navController.navigate(AuthenticationScreen.SignInScreen.route){
            popUpTo(AuthenticationScreen.SplashScreen.route){ inclusive = true }
            launchSingleTop = true
        }
    }

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().background(BackgroundColor)){
        Image(
            painter = painterResource(id = R.drawable.ic_cyclistance_app_icon),
            contentDescription = "App Icon", modifier = Modifier.scale(scale.value))

    }

}