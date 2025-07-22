package com.example.stove

import android.app.Application
import android.util.Log
import com.example.stove.data.AppContainer
import com.example.stove.data.AppDataContainer

class StoveApplication : Application() {

    private var _container: AppContainer? = null

    val container: AppContainer
        get() = _container ?: throw IllegalStateException("AppContainer has not been initialized.")

    override fun onCreate() {
        super.onCreate()
        Log.d("StoveApp", "StoveApplication onCreate: Initializing container")

        try {
            _container = AppDataContainer(this)
            Log.d("StoveApp", "StoveApplication onCreate: Container initialized successful.")
        } catch (e: Exception) {
            Log.e("StoveApp", "StoveApplication onCreate: Error while initializing container!", e)

            throw RuntimeException("Failed to initialize AppDataContainer", e)
        }
    }
}