package com.example.stove.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.stove.core.Resource
import com.example.stove.domain.usecase.designer.GetAddonsUseCase
import com.example.stove.domain.usecase.designer.GetComponentsUseCase
import com.example.stove.domain.usecase.designer.GetOptionsUseCase
import com.example.stove.domain.usecase.designer.GetTypesUseCase
import com.example.stove.presentation.dto.AddonUiModel
import com.example.stove.presentation.dto.ComponentUiModel
import com.example.stove.presentation.dto.OptionUiModel
import com.example.stove.presentation.dto.TypeUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/** Вот и она, UI-модель */
sealed class DesignerUiState {
    data object ENTRY : DesignerUiState()
    data class TYPE(val types: Resource<List<TypeUiModel>>) : DesignerUiState()
    data class COMPONENT(val components: Resource<List<ComponentUiModel>>) : DesignerUiState()
    data class OPTION(val options: Resource<List<OptionUiModel>>) : DesignerUiState()
    data class ADDON(val addons: Resource<List<AddonUiModel>>) : DesignerUiState()

    data object Summary : DesignerUiState()
}

sealed interface DesignerNavigationEvents {
    data object ToStart : DesignerNavigationEvents
}

data class SelectedItems(
    val typeId: Int = -1,
    val typeName: String = "",

    val componentId: Int = -1,
    val componentName: String = "",

    val optionId: Int = -1,
    val optionName: String = "",

    val addonId: Int = -1,
    val addonName: String = ""
)

/**
 * Когда ViewModel начнёт расти, нужно сделать реализацию через UI-модель
 */
@HiltViewModel
class DesignerViewModel @Inject constructor(): ViewModel() {
    private val _navigationEvent = Channel<DesignerNavigationEvents>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val _designerUiState = MutableStateFlow<DesignerUiState>(DesignerUiState.ENTRY)
    val designerUiState: StateFlow<DesignerUiState> = _designerUiState


    private val _selectedItems = MutableStateFlow(SelectedItems())
    val selectedItems: StateFlow<SelectedItems> = _selectedItems

    @Inject
    lateinit var getTypesCase: GetTypesUseCase
    @Inject
    lateinit var getComponentsCase: GetComponentsUseCase
    @Inject
    lateinit var getOptionsCase: GetOptionsUseCase
    @Inject
    lateinit var getAddonsCase: GetAddonsUseCase

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

    fun loadOptions() {
        viewModelScope.launch {
            _designerUiState.value = DesignerUiState.OPTION(Resource.LOADING())
            _designerUiState.value = DesignerUiState.OPTION(
                getOptionsCase.invoke(componentId = _selectedItems.value.componentId)
            )
        }
    }

    fun loadAddons() {
        viewModelScope.launch {
            _designerUiState.value = DesignerUiState.ADDON(Resource.LOADING())
            _designerUiState.value = DesignerUiState.ADDON(
                getAddonsCase.invoke()
            )
        }
    }

    fun updateType(typeId: Int, typeName: String) {
        _selectedItems.update {
            selectedItems -> selectedItems.copy(typeId = typeId, typeName = typeName)
        }
    }

    fun updateComponent(componentId: Int, componentName: String) {
        _selectedItems.update {
            selectedItems -> selectedItems.copy(componentId = componentId, componentName = componentName)
        }
    }

    fun updateOption(optionId: Int, optionName: String) {
        _selectedItems.update {
                selectedItems -> selectedItems.copy(optionId = optionId, optionName = optionName)
        }
    }

    fun updateAddon(addonId: Int, addonName: String) {
        _selectedItems.update {
                selectedItems -> selectedItems.copy(addonId = addonId, addonName = addonName)
        }
    }

    fun order() {

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