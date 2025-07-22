package com.example.stove.data.favourite

import kotlinx.coroutines.flow.Flow

class OfflineFavouriteRepository(private val dao: FavouriteDao) : FavouriteRepository {
    override fun getAllFavourites() : Flow<List<Favourite>> {
        return dao.getAllFavourites()
    }

    override suspend fun insert(favourite: Favourite) {
        dao.insert(favourite)
    }

    override suspend fun delete(favourite: Favourite) {
        dao.delete(favourite)
    }
}