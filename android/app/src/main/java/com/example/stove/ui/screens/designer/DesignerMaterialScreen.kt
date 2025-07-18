package com.example.stove.ui.screens.designer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.stove.R
import com.example.stove.navigation.NavigationDestination
import com.example.stove.ui.screens.CustomButton
import com.example.stove.ui.screens.DesignerOptionCard
import com.example.stove.ui.theme.StoveTheme

object DesignerMaterialDestination : NavigationDestination {
    override val route: String = "DesignerMaterial"
    override val titleRes: Int = R.string.title_constructor
}

@Composable
fun DesignerMaterialScreen() {
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
        Column {
            Row {
                DesignerOptionCard(
                    title = stringResource(R.string.caption_brick),
                    imageRes = R.drawable.brick,
                    isSelected = false,
                    modifier = Modifier
                        .weight(1f)
                        .padding(dimensionResource(R.dimen.padding_large))
                )
                DesignerOptionCard(
                    title = stringResource(R.string.caption_concrete),
                    imageRes = R.drawable.concrete,
                    isSelected = true,
                    modifier = Modifier
                        .weight(1f)
                        .padding(dimensionResource(R.dimen.padding_large))
                )
            }
            Row(modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_large))) {
                CustomButton(
                    labelId = R.string.button_back,
                    textStyle = MaterialTheme.typography.labelLarge,
                    isActiveButton = false,
                    onClickBehavior = { TODO() },
                    modifier = Modifier
                        .padding(end = dimensionResource(R.dimen.padding_small))
                        .weight(1f)
                )
                CustomButton(
                    labelId = R.string.button_next,
                    textStyle = MaterialTheme.typography.labelLarge,
                    isActiveButton = true,
                    onClickBehavior = { TODO() },
                    modifier = Modifier
                        .padding(end = dimensionResource(R.dimen.padding_small))
                        .weight(1f)
                )
            }
        }
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
            DesignerMaterialScreen()
        }
    }
}
