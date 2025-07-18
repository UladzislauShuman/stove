package com.example.stove.ui.screens.designer

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.stove.data.favourite.Favourite
import com.example.stove.data.favourite.FavouriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

class DesignerViewModel(private val favouriteRepository: FavouriteRepository): ViewModel() {

    private val selectedType = MutableStateFlow<String?>(null)
    private val selectedMaterial = MutableStateFlow<String?>(null)

    fun updateType(type: String) {
        selectedType.update {
            type
        }
    }
    fun updateMaterial(material: String) {
        selectedMaterial.update {
            material
        }
    }

    val summaryProject = combine(selectedType, selectedMaterial) { type, material ->
        if(type != null && material != null) {
            Favourite(
                type = type,
                material = material
            )
        } else
            null
    }

}


/**
 *
 * Я НЕ БУДУ ДЕЛАТЬ ЭТО КАК ГОВНО КОТОРОЕ ЩАС
 * Я СДЕЛАЮ НОРМАЛЬНО
 * НАХУЯ Я ПОСТАВИЛ СЕБЕ КАКИЕ-ТО ВРЕМЕННЫЙ РАМКИ ЧИЛЛ
 * А ТО КОД ПОЛНОЕ ГОВНИЩЕ
 *
 * СКОЛЬКО ЗАХАРДКОЖЕНО.....
 *
 * */