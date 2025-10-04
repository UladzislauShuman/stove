package com.example.stove.presentation.ui.screens.designer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.stove.presentation.ui.component.CustomButton
import com.example.stove.presentation.ui.component.InputField
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
    val draft by viewModel.draft.collectAsState()

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(
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
            modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_medium))
        ) {
            InputField(
                labelId = R.string.hint_draft_name,
                onValueChange = { viewModel.updateDraftName(it) },
                value = draft.draftName,
                modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_medium))
            )
            Text(
                text = "Тип печи: " + draft.type?.second,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
                    .align(Alignment.CenterHorizontally)
            )
            draft.componentOptions.forEach { component ->
                Text(
                    text = component.value.componentName + ": " + component.value.optionName,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .align(Alignment.CenterHorizontally)
                )
            }
            Text(
                text = "Дополнительно: " + draft.addons.forEach { addon -> addon.value + ", "},
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
            onClickBehavior = { viewModel.putConfiguration() },
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
