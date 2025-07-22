package com.example.stove.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stove.R
import com.example.stove.navigation.NavigationDestination
import com.example.stove.ui.screens.IconWithBackground
import com.example.stove.ui.theme.StoveTheme

object ProfileDestination : NavigationDestination {
    override val route: String = "Profile"
    override val titleRes: Int = R.string.title_profile
}

@Composable
fun ProfileScreen(
    onClickFavourites: () -> Unit
) {
    Column(
        modifier = Modifier.padding(
            start = dimensionResource(R.dimen.padding_large),
            end = dimensionResource(R.dimen.padding_large),
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(
                    top = dimensionResource(R.dimen.padding_large),
                    bottom = dimensionResource(R.dimen.padding_large)
                )
        ) {
            Surface(
                shape = RoundedCornerShape(dimensionResource(R.dimen.fully_rounded_avatar)),
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_large))
            ) {
                Image(
                    painter = painterResource(R.drawable.empty_avatar),
                    contentDescription = stringResource(R.string.avatar_description),
                    modifier = Modifier.size(dimensionResource(R.dimen.avatar_size))
                )
            }
            Text(
                text = "Константин Антовнович",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "+7 (916) 123-45-67",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = dimensionResource(R.dimen.padding_large))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconWithBackground(
                        painter = painterResource(R.drawable.orders_icon),
                        contentDescription = null,
                        modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_large))
                    )
                    Text(
                        text = stringResource(R.string.menu_orders),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Icon(
                    painter = painterResource(R.drawable.arrow_forward_icon),
                    contentDescription = null
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = dimensionResource(R.dimen.padding_large))
                    .clickable{ onClickFavourites() }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconWithBackground(
                        painter = painterResource(R.drawable.favourites_icon),
                        contentDescription = null,
                        modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_large))
                    )
                    Text(
                        text = stringResource(R.string.menu_favorites),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Icon(
                    painter = painterResource(R.drawable.arrow_forward_icon),
                    contentDescription = null
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconWithBackground(
                        painter = painterResource(R.drawable.logout_icon),
                        contentDescription = null,
                        modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_large))
                    )
                    Text(
                        text = stringResource(R.string.menu_logout),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Icon(
                    painter = painterResource(R.drawable.arrow_forward_icon),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    StoveTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
//            ProfileScreen()
        }
    }
}