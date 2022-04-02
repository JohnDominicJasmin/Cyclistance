package com.example.cyclistance.feature_authentication.presentation.authentication_sign_in

import androidx.annotation.RawRes
import androidx.compose.runtime.Composable

sealed class SignInEventResult {
    object RefreshEmail: SignInEventResult()
    object ShowInternetScreen : SignInEventResult()
    data class ShowAlertDialog(
        val title: String = "",
        val description: String = "",
        @RawRes val imageResId: Int = -1) : SignInEventResult()


}