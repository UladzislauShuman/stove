package com.example.stove.data

import android.content.Context
import com.example.stove.data.favourite.FavouriteDatabase
import com.example.stove.data.favourite.FavouriteRepository
import com.example.stove.data.favourite.FavouriteRepositoryImpl

interface AppContainer {
    val favouriteRepository: FavouriteRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val favouriteRepository by lazy {
        FavouriteRepositoryImpl(FavouriteDatabase.getDatabase(context).getDao())
    }
}