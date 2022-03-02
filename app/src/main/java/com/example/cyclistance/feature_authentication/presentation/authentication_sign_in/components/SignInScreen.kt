package com.example.cyclistance.feature_authentication.presentation.authentication_sign_in.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.cyclistance.navigation.Screens
import com.example.cyclistance.feature_authentication.presentation.common.AuthenticationConstraintsItem
import com.example.cyclistance.feature_authentication.presentation.common.Waves
import com.example.cyclistance.feature_authentication.presentation.theme.*

@Composable
fun SignInScreen(
    navController: NavController?) {


    ConstraintLayout(
        constraintSet = signInConstraints, modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)) {


        AppImageIcon(layoutId = AuthenticationConstraintsItem.IconDisplay.layoutId)
        SignUpTextArea()
        Waves(
            topWaveLayoutId = AuthenticationConstraintsItem.TopWave.layoutId,
            bottomWaveLayoutId = AuthenticationConstraintsItem.BottomWave.layoutId
        )
        SignInTextFieldsArea()
        SignInGoogleAndFacebookButton()

        SignInButton(onClickButton = {

        })


        SignInClickableText(onClick = {
            navController?.navigate(Screens.SignUpScreen.route)
        })

    }


}
