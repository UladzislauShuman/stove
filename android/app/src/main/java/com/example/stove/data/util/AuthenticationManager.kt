package com.example.stove.data.util

import com.example.stove.data.di.NetworkModule
import com.example.stove.data.local.TokenManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class AuthenticationManager @Inject constructor(
    private val tokenManager: TokenManager,
    private val okHttpClientProvider: Provider<OkHttpClient>
) : LogoutAction {
    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated

    init {
        CoroutineScope(Dispatchers.IO).launch {
            _isAuthenticated.value = tokenManager.isLoggedIn()
        }
    }

    override fun logout() {
        CoroutineScope(Dispatchers.IO).launch {
            tokenManager.clearToken()

            val okHttpClient = okHttpClientProvider.get()
            okHttpClient.cache?.evictAll()
            _isAuthenticated.value = false
        }
    }
}
