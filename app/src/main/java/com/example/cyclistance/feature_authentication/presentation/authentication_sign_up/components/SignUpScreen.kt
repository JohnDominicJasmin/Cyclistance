package com.example.cyclistance.feature_authentication.presentation.authentication_sign_up.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.cyclistance.navigation.Screens
import com.example.cyclistance.feature_authentication.presentation.authentication_sign_in.components.AppImageIcon
import com.example.cyclistance.feature_authentication.presentation.authentication_sign_in.components.Waves
import com.example.cyclistance.feature_authentication.presentation.common.AuthenticationConstraintsItem
import com.example.cyclistance.feature_authentication.presentation.theme.BackgroundColor

@Composable
fun SignUpScreen(navController: NavController?) {


    ConstraintLayout(
        constraintSet = signUpConstraints,
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)) {

        AppImageIcon(layoutId = AuthenticationConstraintsItem.IconDisplay.layoutId)
        SignUpTextArea()

        Waves(
            topWaveLayoutId = AuthenticationConstraintsItem.TopWave.layoutId,
            bottomWaveLayoutId = AuthenticationConstraintsItem.BottomWave.layoutId
        )

        SignUpTextFields()
        SignUpButton(onClickButton = {

        })
        SignUpClickableText() {
            navController?.navigate(Screens.SignInScreen.route)
        }


    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(navController = null)
}