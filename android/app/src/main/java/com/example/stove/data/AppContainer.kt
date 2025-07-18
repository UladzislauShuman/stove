package com.example.stove.data

import android.content.Context
import com.example.stove.data.favourite.FavouriteDatabase
import com.example.stove.data.favourite.FavouriteRepository
import com.example.stove.data.favourite.OfflineFavouriteRepository

interface AppContainer {
    val favouriteRepository: FavouriteRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val favouriteRepository by lazy {
        OfflineFavouriteRepository(FavouriteDatabase.getDatabase(context).getDao())
    }
}