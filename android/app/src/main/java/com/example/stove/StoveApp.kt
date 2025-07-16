package com.example.stove

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.stove.model.StoveMenus



@Composable
fun StoveApp() {

}



@Composable
fun StoveBottomAppBar(
    isSelected: StoveMenus,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier
            .padding(
                top = dimensionResource(R.dimen.padding_small),
                bottom = dimensionResource(R.dimen.padding_medium),
                start = dimensionResource(R.dimen.padding_large),
                end = dimensionResource(R.dimen.padding_large)
            ),
        containerColor = MaterialTheme.colorScheme.background,
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = { TODO() }) {
                    Column {
                        if (isSelected.number == 1) {
                            Icon(
                                painter = painterResource(R.drawable.home_active),
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                painter = painterResource(R.drawable.home_passive),
                                contentDescription = null
                            )
                        }
                        Text(
                            text = stringResource(R.string.nav_home),
                            style = MaterialTheme.typography.titleLarge,
                            color = if (isSelected.number == 1) MaterialTheme.colorScheme.onPrimaryContainer
                            else MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
                IconButton(onClick = { TODO() }) {
                    Column {
                        if (isSelected.number == 2) {
                            Icon(
                                painter = painterResource(R.drawable.designer_active),
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                painter = painterResource(R.drawable.designer_passive),
                                contentDescription = null
                            )
                        }
                        Text(
                            text = stringResource(R.string.nav_home),
                            style = MaterialTheme.typography.titleLarge,
                            color = if (isSelected.number == 2) MaterialTheme.colorScheme.onPrimaryContainer
                            else MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
                IconButton(onClick = { TODO() }) {
                    Column {
                        if (isSelected.number == 3) {
                            Icon(
                                painter = painterResource(R.drawable.profile_active),
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                painter = painterResource(R.drawable.profile_passive),
                                contentDescription = null
                            )
                        }
                        Text(
                            text = stringResource(R.string.nav_home),
                            style = MaterialTheme.typography.titleLarge,
                            color = if (isSelected.number == 3) MaterialTheme.colorScheme.onPrimaryContainer
                            else MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoveTopAppBar(
    title: String,
    canNavigateBack: Boolean
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.padding(
            top = dimensionResource(R.dimen.padding_large),
            bottom = dimensionResource(R.dimen.padding_small),
            start = dimensionResource(R.dimen.padding_large),
            end = dimensionResource(R.dimen.padding_large)
        ),
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium
            )
        },
        navigationIcon = {
            if(canNavigateBack) {
                IconButton(onClick = { TODO() } ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_backward_icon),
                        contentDescription = stringResource(R.string.arrow_backward_description)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}