package com.example.stove.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stove.core.Resource
import com.example.stove.data.favourite.FavouriteRepository
import com.example.stove.domain.usecase.GetComponentsUseCase
import com.example.stove.domain.usecase.GetTypesUseCase
import com.example.stove.presentation.model.ComponentUiModel
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
    data class COMPONENT(val components: Resource<List<ComponentUiModel>>) : DesignerUiState()

}

data class SelectedItems(
    val typeId: Int = -1,
    val componentId: Int = -1,
    val optionId: Int = -1,
    val addonId: Int = -1
)

/**
 * Когда ViewModel начнёт расти, нужно сделать реализацию через UI-модель
 */
@HiltViewModel
class DesignerViewModel @Inject constructor(
    private val favouriteRepository: FavouriteRepository,
): ViewModel() {

    private val _designerUiState = MutableStateFlow<DesignerUiState>(DesignerUiState.ENTRY)
    val designerUiState: StateFlow<DesignerUiState> = _designerUiState


    private val _selectedItems = MutableStateFlow(SelectedItems())
    val selectedItems: StateFlow<SelectedItems> = _selectedItems

    @Inject
    lateinit var getTypesCase: GetTypesUseCase
    @Inject
    lateinit var getComponentsCase: GetComponentsUseCase

    fun loadTypes() {
        viewModelScope.launch {
            _designerUiState.value = DesignerUiState.TYPE(Resource.LOADING())
            _designerUiState.value = DesignerUiState.TYPE(
                getTypesCase.invoke()
            )
        }
    }

    fun loadComponents() {
        viewModelScope.launch {
            _designerUiState.value = DesignerUiState.COMPONENT(Resource.LOADING())
            _designerUiState.value = DesignerUiState.COMPONENT(
                getComponentsCase.invoke(_selectedItems.value.typeId)
            )
        }
    }

    fun updateType(typeId: Int) {
        _selectedItems.update {
            selectedItems -> selectedItems.copy(typeId = typeId)
        }
    }
    fun updateComponent(componentId: Int) {
        _selectedItems.update {
            selectedItems -> selectedItems.copy(componentId = componentId)
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