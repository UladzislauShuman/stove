package com.example.stove.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.stove.R

@Composable
fun EntryScreen() {
    Column(
        modifier = Modifier.padding(
            start = dimensionResource(R.dimen.padding_large),
            end = dimensionResource(R.dimen.padding_large)
        )
    ) {
        Column() {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                InputField(
                    labelId = R.string.hint_username,
                    onValueChange = {  },
                    value = "",
                    modifier = Modifier.padding(
                        top = dimensionResource(R.dimen.padding_medium),
                        bottom = dimensionResource(R.dimen.padding_medium)
                    )
                )
                InputField(
                    labelId = R.string.hint_password,
                    onValueChange = {  },
                    value = "",
                    modifier = Modifier.padding(
                        bottom = dimensionResource(R.dimen.padding_medium)
                    )
                )
            }
            Text(
                text = stringResource(R.string.link_forgot_password),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Column() {
                CustomButton(
                    labelId = R.string.button_login,
                    isActiveButton = true,
                    onClickBehavior = { }
                )
                CustomButton(
                    labelId = R.string.button_guest,
                    isActiveButton = false,
                    onClickBehavior = { }
                )
            }
        }
    }
}

@Preview
@Composable
fun EntryScreenPreview() {
    EntryScreen()
}