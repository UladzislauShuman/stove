package com.example.stove.presentation.ui.screens.designer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stove.core.Resource
import com.example.stove.data.favourite.Favourite
import com.example.stove.data.favourite.FavouriteRepository
import com.example.stove.domain.usecase.GetTypesUseCase
import com.example.stove.presentation.model.TypeUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/** Вот и она, UI-модель */
sealed class DesignerUiState {
    data object ENTRY : DesignerUiState()
    data class TYPE(val types: Resource<List<TypeUiModel>>) : DesignerUiState()
}

/**
 * Когда ViewModel начнёт расти, нужно сделать реализацию через UI-модель
 */
@HiltViewModel
class DesignerViewModel(private val favouriteRepository: FavouriteRepository): ViewModel() {

    private val _designerUiState = MutableStateFlow<DesignerUiState>(DesignerUiState.ENTRY)
    val designerUiState: StateFlow<DesignerUiState> = _designerUiState

    @Inject
    private lateinit var getTypesCase: GetTypesUseCase

    fun loadTypes() {
        viewModelScope.launch {
            _designerUiState.value = DesignerUiState.TYPE(Resource.LOADING())
            _designerUiState.value = DesignerUiState.TYPE(
                getTypesCase.invoke()
            )
        }
    }

    private val _selectedType = MutableStateFlow<Int?>(null)
    val selectedType: StateFlow<Int?> = _selectedType

    private val _selectedMaterial = MutableStateFlow<String?>(null)
    val selectedMaterial: StateFlow<String?> = _selectedMaterial

    fun updateType(type: Int) {
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
//        val type = _selectedType.value
//        val material = _selectedMaterial.value
//
//        viewModelScope.launch {
//            if(type == null || material == null) {
//                return@launch
//            } else {
//                val favourite =
//                    Favourite(
//                        type = type,
//                        material = material
//                    )
//                favouriteRepository.insert(favourite)
//            }
//        }
    }
}