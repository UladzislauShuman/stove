package com.example.stove.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stove.R
import com.example.stove.ui.AppViewModelProvider
import com.example.stove.ui.screens.FavouriteCard

@Composable
fun ProfileFavouritesScreen(
    viewModel: ProfileFavouritesViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val favouritesUiState by viewModel.favouritesUiState.collectAsState()

    Column(
        modifier = Modifier.padding(
            start = dimensionResource(R.dimen.padding_large),
            end = dimensionResource(R.dimen.padding_large)
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            contentPadding = PaddingValues(
                top = dimensionResource(R.dimen.padding_large),
                bottom = dimensionResource(R.dimen.padding_large)
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items = favouritesUiState.favourites, key = { it.id }) { favourite ->
                FavouriteCard(
                    favourite = favourite,
                    modifier = Modifier.padding(
                        bottom = dimensionResource(R.dimen.padding_medium)
                    )
                )
            }
        }
    }
}