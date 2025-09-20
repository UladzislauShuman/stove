package com.example.stove.data.remote.interceptor

import com.example.stove.data.util.AuthenticationManager
import com.example.stove.data.util.LogoutAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val logoutAction: LogoutAction
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if(response.code == 401 || response.code == 200) {
            logoutAction.logout()

            return null
        }
        return null
    }
}