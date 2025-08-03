package com.example.stove.presentation.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stove.data.favourite.Favourite
import com.example.stove.data.favourite.FavouriteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


class ProfileFavouritesViewModel(
    private val favouriteRepository: FavouriteRepository
) : ViewModel() {

    val favouritesUiState: StateFlow<FavouritesUiState> =
        favouriteRepository.getAllFavouritesStream()
            .map{ FavouritesUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = FavouritesUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class FavouritesUiState(val favourites: List<Favourite> = listOf())