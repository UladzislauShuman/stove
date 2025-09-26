package com.example.stove.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stove.core.AuthState
import com.example.stove.data.remote.interceptor.AuthAuthenticator
import com.example.stove.domain.usecase.auth.CheckAuthUseCase
import com.example.stove.domain.usecase.auth.LogoutUseCase
import com.example.stove.domain.usecase.auth.ObserveAuthStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GlobalViewModel @Inject constructor(
    private val authAuthenticator: AuthAuthenticator,

    private val logoutUseCase: LogoutUseCase,

    private val observeAuthStateUseCase: ObserveAuthStateUseCase,

    private val checkAuthUseCase: CheckAuthUseCase
) : ViewModel() {
    val authState: StateFlow<AuthState> = observeAuthStateUseCase.invoke()


    init {
        viewModelScope.launch {
            checkAuthUseCase.invoke()
        }
        viewModelScope.launch {
            authAuthenticator.authEventFlow.collect {
                Log.d("GlobalViewModel", "Received 401, calling logout!")
                logoutUseCase.invoke()
            }
        }
    }
}