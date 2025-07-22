package com.example.stove.ui.screens.designer

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
import com.example.stove.R
import com.example.stove.model.StoveCharacteristics
import com.example.stove.model.StoveProperties
import com.example.stove.navigation.NavigationDestination
import com.example.stove.ui.screens.DesignerButtonsRow
import com.example.stove.ui.screens.DesignerOptionCard
import com.example.stove.ui.theme.StoveTheme

object DesignerMaterialDestination : NavigationDestination {
    override val route: String = "DesignerMaterial"
    override val titleRes: Int = R.string.title_constructor
}

@Composable
fun DesignerMaterialScreen(
    stoveMaterials: List<StoveCharacteristics> = StoveProperties.materials,
    viewModel: DesignerViewModel,
    backBehavior: () -> Unit,
    nextBehavior: () -> Unit
) {
    val selectedMaterial by viewModel.selectedMaterial.collectAsState()

    Column(
        modifier = Modifier.padding(
            start = dimensionResource(R.dimen.padding_large),
            end = dimensionResource(R.dimen.padding_large)
        )
    ) {
        Text(
            text = stringResource(R.string.title_material),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(
                top = dimensionResource(R.dimen.padding_medium),
                bottom = dimensionResource(R.dimen.padding_small)
            )
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(dimensionResource(R.dimen.cell_size)),
        ) {
            items(items = stoveMaterials, key = { material -> material.id }) { material ->
                val materialName = stringResource(material.nameRes)

                DesignerOptionCard(
                    titleRes = material.nameRes,
                    imageRes = material.imageRes,
                    isSelected = selectedMaterial == materialName,
                    onClickBehavior = {
                        viewModel.updateMaterial(materialName)
                    }
                )
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
