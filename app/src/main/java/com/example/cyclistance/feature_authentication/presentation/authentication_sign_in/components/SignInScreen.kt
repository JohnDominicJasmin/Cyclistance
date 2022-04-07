package com.example.cyclistance.feature_authentication.presentation.authentication_sign_in.components

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext

import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cyclistance.common.AlertDialogData
import com.example.cyclistance.common.SetupAlertDialog
import com.example.cyclistance.feature_authentication.presentation.authentication_email.EmailAuthEvent
import com.example.cyclistance.feature_authentication.presentation.authentication_email.EmailAuthEventResult

import com.example.cyclistance.feature_authentication.presentation.authentication_email.EmailAuthViewModel
import com.example.cyclistance.feature_authentication.presentation.authentication_sign_in.SignInEvent
import com.example.cyclistance.feature_authentication.presentation.authentication_sign_in.SignInEventResult
import com.example.cyclistance.feature_authentication.presentation.authentication_sign_in.SignInViewModel
import com.example.cyclistance.feature_authentication.presentation.common.AppImageIcon
import com.example.cyclistance.navigation.Screens
import com.example.cyclistance.feature_authentication.presentation.common.AuthenticationConstraintsItem
import com.example.cyclistance.feature_authentication.presentation.common.Waves
import com.example.cyclistance.theme.BackgroundColor
import kotlinx.coroutines.flow.collectLatest



@Composable
fun SignInScreen(
    onBackPressed:() -> Unit,
    signInViewModel: SignInViewModel = hiltViewModel(),
    emailAuthViewModel: EmailAuthViewModel = hiltViewModel(),
    navigateTo: (destination: String, popUpToDestination: String?) -> Unit) {

    val signInStateValue = signInViewModel.state.value
    val emailAuthStateValue = emailAuthViewModel.state.value
    val context = LocalContext.current


    BackHandler(enabled = true, onBack = onBackPressed)



    signInStateValue.also{ signInState ->

            var alertDialogState by remember { mutableStateOf(AlertDialogData()) }

            LaunchedEffect(key1 = true) {

                signInViewModel.eventFlow.collectLatest { signInEvent ->

                    when (signInEvent) {

                        is SignInEventResult.RefreshEmail -> {
                            emailAuthViewModel.onEvent(EmailAuthEvent.RefreshEmail)
                        }
                        is SignInEventResult.ShowNoInternetScreen -> {
                            navigateTo(Screens.NoInternetScreen.route, null)
                        }
                        is SignInEventResult.ShowAlertDialog -> {
                            alertDialogState = AlertDialogData(
                                title = signInEvent.title,
                                description = signInEvent.description,
                                resId = signInEvent.imageResId)
                        }
                        is SignInEventResult.ShowMappingScreen -> {
                            navigateTo(Screens.MappingScreen.route, Screens.SignInScreen.route)
                        }
                        is SignInEventResult.ShowToastMessage -> {
                            Toast.makeText(context, signInEvent.message, Toast.LENGTH_SHORT).show()
                        }


                    }
                }
            }


            LaunchedEffect(key1 = true) {
                emailAuthViewModel.eventFlow.collectLatest { emailAuthEvent ->
                    when (emailAuthEvent) {
                        is EmailAuthEventResult.ShowNoInternetScreen -> {
                            navigateTo(Screens.NoInternetScreen.route, null)
                        }
                        is EmailAuthEventResult.ShowAlertDialog -> {
                            alertDialogState = AlertDialogData(
                                title = emailAuthEvent.title,
                                description = emailAuthEvent.description,
                                resId = emailAuthEvent.imageResId)
                        }
                        is EmailAuthEventResult.ShowMappingScreen -> {
                            navigateTo(Screens.MappingScreen.route, Screens.SignInScreen.route)
                        }
                        is EmailAuthEventResult.ShowToastMessage -> {
                            Toast.makeText(context, emailAuthEvent.message, Toast.LENGTH_SHORT).show()
                        }
                        is EmailAuthEventResult.UserEmailIsNotVerified -> {
                            navigateTo(Screens.EmailAuthScreen.route, Screens.SignInScreen.route)
                        }
                    }
                }
            }

            Column(

                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackgroundColor)) {

                ConstraintLayout(
                    constraintSet = signInConstraints,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BackgroundColor)) {


                    AppImageIcon(layoutId = AuthenticationConstraintsItem.IconDisplay.layoutId)
                    SignUpTextArea()

                    if (alertDialogState.run { title.isNotEmpty() || description.isNotEmpty() }) {
                        SetupAlertDialog(
                            alertDialog = alertDialogState,
                            onDismissRequest = {
                                alertDialogState = AlertDialogData()
                            })
                    }

                    Waves(
                        topWaveLayoutId = AuthenticationConstraintsItem.TopWave.layoutId,
                        bottomWaveLayoutId = AuthenticationConstraintsItem.BottomWave.layoutId
                    )


                    SignInTextFieldsArea(
                        state = signInState,
                        signInViewModel = signInViewModel,
                        keyboardActionOnDone = { signInViewModel.onEvent(SignInEvent.SignInDefault) })

                    SignInGoogleAndFacebookSection(
                        facebookButtonOnClick = {

                        },
                        googleSignInButtonOnClick = {

                        }
                    )

                    SignInButton(onClickButton = {
                        signInViewModel.onEvent(SignInEvent.SignInDefault)
                    })


                    SignInClickableText(onClick = {
                        navigateTo(Screens.SignUpScreen.route, null)
                    })

                    if (signInState.isLoading || emailAuthStateValue.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.layoutId(AuthenticationConstraintsItem.ProgressBar.layoutId)
                        )
                    }

                }


            }
        }
    }





