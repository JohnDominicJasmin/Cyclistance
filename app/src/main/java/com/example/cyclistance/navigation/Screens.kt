package com.example.cyclistance.navigation

sealed class Screens(val route:String){

    object SplashScreen: Screens(route = "splash_screen")
    object SignInScreen: Screens(route = "sign_in_screen")
    object SignUpScreen: Screens(route = "sign_up_screen")
    object EmailAuthScreen: Screens(route = "email_auth_screen")
    object IntroSliderScreen: Screens(route="intro_slider_screen")
    object MappingScreen: Screens(route="mapping_screen")
    object NoInternetScreen: Screens(route="no_internet_screen")
    object CancellationScreen: Screens(route="cancellation_screen")
    object ConfirmDetailsScreen: Screens(route="confirm_details_screen")
    object RescueRequestScreen: Screens(route="rescue_request_screen")
    object ChangePasswordScreen: Screens(route="change_password_screen")
    object EditProfileScreen: Screens(route="edit_profile_screen")
    object SettingScreen: Screens(route="setting_screen")
}
