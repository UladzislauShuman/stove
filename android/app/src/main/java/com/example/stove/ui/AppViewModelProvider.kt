package com.example.stove.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.stove.StoveApplication
import com.example.stove.ui.screens.designer.DesignerViewModel
import com.example.stove.ui.screens.profile.ProfileFavouritesViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            DesignerViewModel(
                stoveApplication().container.favouriteRepository
            )
        }
        initializer {
            ProfileFavouritesViewModel(
                stoveApplication().container.favouriteRepository
            )
        }
    }
}

fun CreationExtras.stoveApplication() : StoveApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as StoveApplication)