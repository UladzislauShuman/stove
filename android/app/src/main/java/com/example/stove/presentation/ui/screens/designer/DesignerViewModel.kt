package com.example.stove.presentation.ui.screens.designer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stove.data.favourite.Favourite
import com.example.stove.data.favourite.FavouriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


/**
 * Когда ViewModel начнёт расти, нужно сделать реализацию через UI-модель
 */
class DesignerViewModel(private val favouriteRepository: FavouriteRepository): ViewModel() {

    private val _selectedType = MutableStateFlow<String?>(null)
    val selectedType: StateFlow<String?> = _selectedType

    private val _selectedMaterial = MutableStateFlow<String?>(null)
    val selectedMaterial: StateFlow<String?> = _selectedMaterial


    fun updateType(type: String) {
        _selectedType.update {
            type
        }
    }
    fun updateMaterial(material: String) {
        _selectedMaterial.update {
            material
        }
    }

    fun addToFavourites() {
        val type = _selectedType.value
        val material = _selectedMaterial.value

        viewModelScope.launch {
            if(type == null || material == null) {
                return@launch
            } else {
                val favourite =
                    Favourite(
                        type = type,
                        material = material
                    )
                favouriteRepository.insert(favourite)
            }
        }
    }
}