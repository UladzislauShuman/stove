package com.example.stove.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stove.core.Resource
import com.example.stove.domain.dto.auth.Login
import com.example.stove.domain.usecase.auth.LoginUseCase
import com.example.stove.presentation.dto.LoginUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow


data class InputInfo(
    val email: String = "",
    val password: String = "",
    val phoneNumber: String = "",
    val fullName: String = ""
)

sealed class NavigationEvent {
    data object ToMainApp: NavigationEvent()
    data object ToRegister: NavigationEvent()
}

sealed interface LoginUiState {
    data object Waiting : LoginUiState
    data object Success: LoginUiState
    data class Failure(val message: String): LoginUiState
}

sealed interface RegisterUiState {
    data object Waiting : RegisterUiState
    data object Success: RegisterUiState
    data class Failure(val message: String): RegisterUiState
}

sealed interface AuthUiState {
    data class Login(val state: LoginUiState) : AuthUiState
    data class Register(val state: RegisterUiState) : AuthUiState
}

@HiltViewModel
class AuthViewModel @Inject constructor(

) : ViewModel() {
    private val _navigationEvents = Channel<NavigationEvent>()
    val navigationEvent = _navigationEvents.receiveAsFlow()

    private val _authUiState = MutableStateFlow(AuthUiState.Login(LoginUiState.Waiting))
    val authUiState: StateFlow<AuthUiState> = _authUiState

    private val _inputInfo = MutableStateFlow(InputInfo())
    val inputInfo: StateFlow<InputInfo> = _inputInfo

    @Inject
    lateinit var loginCase: LoginUseCase

    fun login() {
        viewModelScope.launch {
            if(_inputInfo.value.email != "" && _inputInfo.value.password != "") {

                val request = LoginUiModel(
                    email = _inputInfo.value.email,
                    password = _inputInfo.value.password
                )

                val result =  loginCase.invoke(request.toDomain())
                when(result) {
                    is Resource.SUCCESS<*> -> {
                        _authUiState.value = AuthUiState.Login(LoginUiState.Success)
                        _navigationEvents.send(NavigationEvent.ToMainApp)
                    }
                    is Resource.FAILURE -> {
                        _authUiState.value = AuthUiState.Login(LoginUiState.Failure(result.error.message ?: "Unknown error"))
                    }
                    else -> _authUiState.value = AuthUiState.Login(LoginUiState.Waiting)
                }
            }
        }
    }


    private fun LoginUiModel.toDomain() = Login(email, password)
}