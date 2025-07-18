package com.example.stove.data.favourite

import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {
    suspend fun getAllFavourites() : Flow<List<Favourite>>

    suspend fun insert(favourite: Favourite)

    suspend fun delete(favourite: Favourite)
}