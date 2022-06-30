package com.example.cyclistance.feature_settings.presentation.setting_edit_profile

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cyclistance.common.MappingConstants.IMAGE_PLACEHOLDER_URL
import com.example.cyclistance.feature_authentication.domain.use_case.AuthenticationUseCase
import com.example.cyclistance.feature_main_screen.domain.exceptions.MappingExceptions
import com.example.cyclistance.feature_settings.domain.use_case.SettingUseCase
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val settingUseCase: SettingUseCase,
    private val authUseCase: AuthenticationUseCase) : ViewModel() {

    private val _state = mutableStateOf(EditProfileState())
    val state = _state

    private val _eventFlow = MutableSharedFlow<EditProfileUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    private fun getName(): String {
        val email = authUseCase.getEmailUseCase()
        return authUseCase.getNameUseCase() ?: email?.run {
            val index = this.indexOf('@')
            this.substring(0, index)
        } ?: throw MappingExceptions.NameException()
    }

    private fun getPhotoUrl(): String {
        return authUseCase.getPhotoUrlUseCase()?.toString()
               ?: IMAGE_PLACEHOLDER_URL
    }

    private suspend fun getPhoneNumber() = authUseCase.getPhoneNumberUseCase()


    init {
        onEvent(event = EditProfileEvent.LoadName)
        onEvent(event = EditProfileEvent.LoadPhoto)
        onEvent(event = EditProfileEvent.LoadPhoneNumber)
    }


    fun onEvent(event: EditProfileEvent) {

        with(state.value) {
            when (event) {
                is EditProfileEvent.Save -> {
                    updateUserProfile()
                }
                is EditProfileEvent.EnteredPhoneNumber -> {
                    _state.value = copy(
                        phoneNumber = event.phoneNumber,
                        phoneNumberErrorMessage = "")
                }
                is EditProfileEvent.EnteredName -> {
                    _state.value = copy(name = event.name, nameErrorMessage = "")
                }
                is EditProfileEvent.NewImageUri -> {
                    _state.value = copy(imageUri = event.uri)
                }
                is EditProfileEvent.NewBitmapPicture -> {
                    _state.value = copy(bitmap = event.bitmap)
                }
                is EditProfileEvent.LoadPhoto -> {
                    viewModelScope.launch {
                        runCatching {
                            _state.value = copy(
                                photoUrl = getPhotoUrl(),
                                isLoading = true
                            )
                        }.onSuccess {
                            _state.value = copy(isLoading = false)
                        }
                    }
                }
                is EditProfileEvent.SaveImageToGallery -> {
                    viewModelScope.launch(context = Dispatchers.IO) {
                       bitmap?.let { bitmap ->
                           runCatching {
                               settingUseCase.saveImageToGalleryUseCase(bitmap)
                           }.onSuccess { uri ->
                               _state.value = copy(imageUri = uri)
                           }.onFailure{
                               Timber.e("Saving Image to Gallery: ${it.message}")
                           }
                       }
                    }
                }
                is EditProfileEvent.LoadPhoneNumber -> {
                    viewModelScope.launch {
                        runCatching {
                            _state.value = copy(
                                phoneNumber = TextFieldValue(text = getPhoneNumber()),
                                isLoading = true
                            )
                        }.onSuccess {
                            _state.value = copy(isLoading = false)
                        }.onFailure { exception ->
                            _state.value = copy(
                                isLoading = false,
                                phoneNumberErrorMessage = exception.message
                                                          ?: "Field cannot be blank."
                            )
                        }
                    }
                }
                is EditProfileEvent.LoadName -> {
                    viewModelScope.launch {
                        runCatching {
                            _state.value = copy(
                                name = TextFieldValue(text = getName()),
                                isLoading = true
                            )
                        }.onSuccess {
                            _state.value = copy(isLoading = false)
                        }.onFailure { exception ->
                            _state.value = copy(
                                isLoading = false,
                                nameErrorMessage = exception.message ?: "Field cannot be blank."
                            )
                        }
                    }
                }

            }
        }
    }


    private fun updateUserProfile() {
        viewModelScope.launch {
            with(state.value) {
                runCatching {
                    _state.value = copy(isLoading = true)
                    authUseCase.updateProfileUseCase(
                        photoUri = run {
                            imageUri?.let { localImageUri ->
                                authUseCase.uploadImageUseCase(localImageUri)
                            }
                        },
                        name = this.name.text)

                    authUseCase.updatePhoneNumberUseCase(phoneNumber.text)
                }.onSuccess {
                    _state.value = copy(isLoading = false)
                    _eventFlow.emit(EditProfileUiEvent.ShowMappingScreen)
                }.onFailure { exception ->
                    _state.value = copy(isLoading = false)
                    when (exception) {
                        is MappingExceptions.PhoneNumberException -> {
                            _state.value = copy(phoneNumberErrorMessage = exception.message!!)
                        }
                        is MappingExceptions.NameException -> {
                            _state.value = copy(nameErrorMessage = exception.message!!)
                        }
                        else -> {
                            Timber.e("Update Profile: ${exception.message}")
                        }
                    }
                }

            }
        }
    }
}