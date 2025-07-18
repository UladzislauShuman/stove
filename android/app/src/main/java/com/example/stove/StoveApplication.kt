package com.example.stove

import android.app.Application
import com.example.stove.data.AppContainer
import com.example.stove.data.AppDataContainer

class StoveApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        container = AppDataContainer(this)
    }
}