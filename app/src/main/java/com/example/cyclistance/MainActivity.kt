package com.example.cyclistance

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import androidx.navigation.compose.rememberNavController
import com.example.cyclistance.feature_authentication.domain.util.ActivityResultCallbackManager
import com.example.cyclistance.feature_authentication.domain.util.LocalActivityResultCallbackManager
import com.example.cyclistance.feature_settings.presentation.setting_main_screen.SettingEvent
import com.example.cyclistance.feature_settings.presentation.setting_main_screen.SettingViewModel
import com.example.cyclistance.navigation.Navigation
import com.example.cyclistance.theme.CyclistanceTheme
import com.facebook.FacebookSdk.sdkInitialize
import com.facebook.appevents.AppEventsLogger
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.mapbox.mapboxsdk.Mapbox
import dagger.hilt.android.AndroidEntryPoint

const val CLICK_TIME_INTERVAL = 1800

@ExperimentalPermissionsApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var callbackManager = ActivityResultCallbackManager()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        sdkInitialize(applicationContext);
        AppEventsLogger.activateApp(application);


        Mapbox.getInstance(
            this,
            getString(R.string.MapsDownloadToken))

        var backPressedTime = 0L
        val onBackPressed = {
            val isDoubleClicked = backPressedTime + CLICK_TIME_INTERVAL > System.currentTimeMillis()

            if (isDoubleClicked) {
                this.finish()
            } else {
                Toast.makeText(this, "Tap again to exit.", Toast.LENGTH_SHORT).show()
                backPressedTime = System.currentTimeMillis()
            }
        }


        setContent {
            val settingViewModel: SettingViewModel = hiltViewModel()
            val isDarkThemeLiveData:LiveData<Boolean> = settingViewModel.isDarkTheme
            val isDarkThemeState by isDarkThemeLiveData.observeAsState(initial = isSystemInDarkTheme())

            CyclistanceTheme(darkTheme = isDarkThemeState) {

                CompositionLocalProvider(LocalActivityResultCallbackManager provides callbackManager) {
                    Navigation(
                        navController = rememberNavController(),
                        onToggleTheme = { settingViewModel.onEvent(event = SettingEvent.ToggleTheme) },
                        isDarkTheme = isDarkThemeState,
                        isDarkThemeLiveData = isDarkThemeLiveData,
                        onBackPressed = onBackPressed)

                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (!callbackManager.onActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}






