package com.example.cyclistance.feature_main_screen.presentation.mapping_confirm_details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cyclistance.feature_authentication.domain.use_case.AuthenticationUseCase
import com.example.cyclistance.feature_main_screen.data.remote.dto.ConfirmationDetails
import com.example.cyclistance.feature_main_screen.data.remote.dto.Status
import com.example.cyclistance.feature_main_screen.data.remote.dto.UserAssistance
import com.example.cyclistance.feature_main_screen.domain.exceptions.MappingExceptions
import com.example.cyclistance.feature_main_screen.domain.model.User
import com.example.cyclistance.feature_main_screen.domain.use_case.MappingUseCase
import com.example.cyclistance.feature_main_screen.presentation.mapping_main_screen.MappingUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ConfirmDetailsViewModel @Inject constructor(
    private val authUseCase: AuthenticationUseCase,
    private val mappingUseCase: MappingUseCase) : ViewModel() {

    private val _state: MutableState<ConfirmDetailsState> = mutableStateOf(ConfirmDetailsState())
    val state by _state

    private val _eventFlow: MutableSharedFlow<ConfirmDetailsUiEvent> = MutableSharedFlow()
    val eventFlow = _eventFlow.asSharedFlow()

    private fun getId():String? = authUseCase.getIdUseCase()

    //todo: load saved bike type from data store
    fun onEvent(event: ConfirmDetailsEvent) {
            when (event) {
                is ConfirmDetailsEvent.Save -> {
                    viewModelScope.launch {
                        updateUser(state)
                    }
                }
                is ConfirmDetailsEvent.SelectBikeType -> {
                    _state.value = state.copy(bikeType = event.bikeType, bikeTypeErrorMessage = "")
                }
                is ConfirmDetailsEvent.EnteredMessage -> {
                    _state.value = state.copy(message = event.message)
                }
                is ConfirmDetailsEvent.SelectDescription -> {
                    _state.value = state.copy(description = event.description, descriptionErrorMessage = "")
                }

        }
    }


    private suspend fun updateUser(confirmDetailsState: ConfirmDetailsState){
        runCatching {
            _state.value = state.copy(isLoading = true)
            mappingUseCase.updateUserUseCase(
                itemId = getId() ?: return,
                user = User(
                    userNeededHelp = true,
                    userAssistance = UserAssistance(
                        confirmationDetails = ConfirmationDetails(
                            bikeType = confirmDetailsState.bikeType,
                            description = confirmDetailsState.description,
                            message = confirmDetailsState.message.text),
                        status = Status(started = true)
                    )
                ))
        }.onSuccess {
            _state.value = state.copy(isLoading = false)
            _eventFlow.emit(value = ConfirmDetailsUiEvent.ShowMappingScreen)
        }.onFailure { exception ->
            _state.value = state.copy(isLoading = false)
            when(exception){
                is MappingExceptions.UnexpectedErrorException -> {
                    _eventFlow.emit(
                        ConfirmDetailsUiEvent.ShowToastMessage(
                            message = exception.message ?: "",
                        ))
                }
                is MappingExceptions.NoInternetException -> {
                    _eventFlow.emit(ConfirmDetailsUiEvent.ShowNoInternetScreen)
                }
                is MappingExceptions.BikeTypeException -> {
                    _state.value = state.copy(bikeTypeErrorMessage = exception.message!!)
                }
                is MappingExceptions.DescriptionException -> {
                    _state.value = state.copy(descriptionErrorMessage = exception.message!!)
                }


            }
        }
    }
}