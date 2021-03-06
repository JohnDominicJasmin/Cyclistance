package com.example.cyclistance.feature_settings.presentation.setting_edit_profile

sealed class EditProfileUiEvent {
    object ShowMappingScreen : EditProfileUiEvent()
    data class ShowToastMessage(val message: String) : EditProfileUiEvent()
}