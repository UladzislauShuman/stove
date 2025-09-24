package com.example.stove.presentation.ui.screens.designer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stove.R
import com.example.stove.presentation.navigation.NavigationDestination
import com.example.stove.presentation.ui.screens.component.CustomButton
import com.example.stove.presentation.ui.theme.StoveTheme
import com.example.stove.presentation.viewmodel.DesignerViewModel

object DesignerSummaryDestination : NavigationDestination {
    override val route: String = "DesignerSummary"
    override val titleRes: Int = R.string.title_constructor
}

@Composable
fun DesignerSummaryScreen(
    viewModel: DesignerViewModel
) {
    val selectedItems by viewModel.selectedItems.collectAsState()

    Column(
        modifier = Modifier.padding(
            start = dimensionResource(R.dimen.padding_large),
            end = dimensionResource(R.dimen.padding_large)
        )
    ) {
        Text(
            text = stringResource(R.string.title_summary),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(
                top = dimensionResource(R.dimen.padding_medium),
                bottom = dimensionResource(R.dimen.padding_small)
            )
        )
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(
                top = dimensionResource(R.dimen.padding_medium),
                bottom = dimensionResource(R.dimen.padding_medium)
            )
        ) {
            Text(
                text = "Тип печи: " + selectedItems.typeId,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Компонент печи: " + selectedItems.componentName,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Настройка компонента: " + selectedItems.optionName,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Дополнительно: " + selectedItems.addonName,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
                    .align(Alignment.CenterHorizontally)
            )
        }
        CustomButton(
            labelId = R.string.button_order,
            textStyle = MaterialTheme.typography.labelMedium,
            isActiveButton = true,
            onClickBehavior = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(R.dimen.padding_large),
                    bottom = dimensionResource(R.dimen.padding_medium)
                )
        )
        CustomButton(
            labelId = R.string.button_add_to_favourites,
            textStyle = MaterialTheme.typography.labelLarge,
            isActiveButton = false,
            onClickBehavior = {   },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = dimensionResource(R.dimen.padding_medium)
                )
        )
    }
}

@Preview
@Composable
fun DesignerSummaryScreenPreview() {
    StoveTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            DesignerSummaryScreen(
//                backBehavior = {},
//                nextBehavior = {},
                viewModel = viewModel()
            )
        }
    }
}
