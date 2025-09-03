package com.example.stove.presentation.ui.screens.designer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import com.example.stove.R
import com.example.stove.core.Resource
import com.example.stove.presentation.model.StoveCharacteristics
import com.example.stove.presentation.model.StoveProperties
import com.example.stove.presentation.navigation.NavigationDestination
import com.example.stove.presentation.ui.screens.DesignerButtonsRow
import com.example.stove.presentation.ui.screens.DesignerOptionCard
import com.example.stove.presentation.ui.theme.StoveTheme
import com.example.stove.presentation.ui.viewmodel.DesignerUiState
import com.example.stove.presentation.ui.viewmodel.DesignerViewModel

object DesignerMaterialDestination : NavigationDestination {
    override val route: String = "DesignerMaterial"
    override val titleRes: Int = R.string.title_constructor
}

@Composable
fun DesignerComponentScreen(
    stoveMaterials: List<StoveCharacteristics> = StoveProperties.materials,
    viewModel: DesignerViewModel,
    backBehavior: () -> Unit,
    nextBehavior: () -> Unit
) {
    val selectedItems by viewModel.selectedItems.collectAsState()
    val uiState by viewModel.designerUiState.collectAsState()

    Column(
        modifier = Modifier
            .padding(
            start = dimensionResource(R.dimen.padding_large),
            end = dimensionResource(R.dimen.padding_large)
        )
    ) {
        Text(
            text = stringResource(R.string.title_component),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(
                top = dimensionResource(R.dimen.padding_medium),
                bottom = dimensionResource(R.dimen.padding_small)
            )
        )
        val currentState = uiState
        if(currentState is DesignerUiState.COMPONENT) {
            when(currentState.components) {
                is Resource.SUCCESS -> {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(dimensionResource(R.dimen.cell_size)),
                    ) {
                        items(items = currentState.components.data, key = { component -> component.id }) { component ->
                            val componentId = component.id

                            DesignerOptionCard(
                                title = component.name,
                                imageUri = "".toUri(),
                                isSelected = selectedItems.componentId == componentId,
                                onClickBehavior = {
                                    viewModel.updateComponent(componentId)
                                }
                            )
                        }
                    }
                }

                is Resource.FAILURE -> {
                    Text(
                        text = "Ошибка загрузки: ${currentState.components.error.message}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(
                            top = dimensionResource(R.dimen.padding_medium),
                            bottom = dimensionResource(R.dimen.padding_small)
                        )
                    )
                }

                is Resource.LOADING -> {
                    Text(
                        text = "Загрузка...",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(
                            top = dimensionResource(R.dimen.padding_medium),
                            bottom = dimensionResource(R.dimen.padding_small)
                        )
                    )
                }
            }
        }
        DesignerButtonsRow(
            backBehavior = { backBehavior() },
            nextBehavior = { nextBehavior() }
        )
    }
}

@Preview
@Composable
fun DesignerMaterialScreenPreview() {
    StoveTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
//            DesignerMaterialScreen()
        }
    }
}
