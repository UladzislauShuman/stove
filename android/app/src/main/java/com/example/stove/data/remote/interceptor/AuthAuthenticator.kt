package com.example.stove.data.remote.interceptor

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthAuthenticator @Inject constructor() : Authenticator {
    private val _authEventFlow = MutableSharedFlow<Unit>()
    val authEventFlow: SharedFlow<Unit> = _authEventFlow

    override fun authenticate(route: Route?, response: Response): Request? {
        if(response.code == 401 || response.code == 200) {
            CoroutineScope(Dispatchers.IO).launch {
                _authEventFlow.emit(Unit)
            }

            return null
        }
        return null
    }
}