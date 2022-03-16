package com.example.cyclistance.feature_authentication.presentation.authentication_email

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cyclistance.feature_authentication.domain.use_case.AuthenticationUseCase
import com.example.cyclistance.feature_authentication.presentation.common.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailAuthViewModel @Inject constructor(
    private val authUseCase: AuthenticationUseCase) : ViewModel() {

    private val _reloadState: MutableState<AuthState<Boolean>> = mutableStateOf(AuthState())
    val reloadState: State<AuthState<Boolean>> = _reloadState

    private val _isEmailVerifyState: MutableState<AuthState<Boolean>> =
        mutableStateOf(AuthState<Boolean>())
    val isEmailVerifyState: State<AuthState<Boolean>> = _isEmailVerifyState

    private val _sendEmailVerificationState: MutableState<AuthState<Boolean>> =
        mutableStateOf(AuthState<Boolean>())
    val sendEmailVerification: State<AuthState<Boolean>> = _sendEmailVerificationState

    fun reloadEmail() {
        viewModelScope.launch {

            kotlin.runCatching {
                _reloadState.value = AuthState(isLoading = true)
                authUseCase.reloadEmailUseCase()
            }.onSuccess { result ->
                _reloadState.value = AuthState(isLoading = false, result = result)
            }.onFailure {
                    _reloadState.value = AuthState(
                        isLoading = false,
                        result = false,
                        error = it.message ?: "An unexpected error occurred.")
                }

        }
    }

    fun isEmailVerified() =
        mutableStateOf(AuthState<Boolean>(result = authUseCase.isEmailVerifiedUseCase()))


    fun sendEmailVerification() {
        viewModelScope.launch {
            kotlin.runCatching {
                _sendEmailVerificationState.value = AuthState(isLoading = true)
                val result = authUseCase.sendEmailVerificationUseCase()
                _sendEmailVerificationState.value =
                    AuthState(isLoading = false, result = result)

            }.onFailure {
                _sendEmailVerificationState.value = AuthState(
                    isLoading = false,
                    result = false,
                    error = it.message ?: "An unexpected error occurred")
            }
        }
    }

}