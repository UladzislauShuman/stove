package com.example.stove

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.stove.model.StoveMenus
import com.example.stove.navigation.StoveNavGraph
import com.example.stove.ui.screens.designer.DesignerEntryDestination
import com.example.stove.ui.screens.home.HomeDestination
import com.example.stove.ui.screens.profile.ProfileDestination


@Composable
fun StoveApp(navController: NavHostController = rememberNavController()) {
    StoveNavGraph(navController = navController)
}


@Composable
fun StoveBottomAppBar(
    navigateHome: () -> Unit,
    navigateDesigner: () -> Unit,
    navigateProfile: () -> Unit,
    isSelected: StoveMenus,
    modifier: Modifier = Modifier
) {
    val navigationIconColors = NavigationBarItemColors(
        selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
        selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
        unselectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
        unselectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
        disabledIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
        disabledTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
        selectedIndicatorColor = MaterialTheme.colorScheme.background
    )

    NavigationBar(
        modifier = modifier.navigationBarsPadding(),
        containerColor = MaterialTheme.colorScheme.background
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    painter = if(isSelected == StoveMenus.HOME)
                        painterResource(R.drawable.home_active)
                    else
                        painterResource(R.drawable.home_passive),
                    contentDescription = null,
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.nav_home),
                    style = MaterialTheme.typography.titleLarge
                )
            },
            selected = isSelected == StoveMenus.HOME,
            onClick = navigateHome,
            colors = navigationIconColors
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = if(isSelected == StoveMenus.DESIGNER)
                        painterResource(R.drawable.designer_active)
                    else
                        painterResource(R.drawable.designer_passive),
                    contentDescription = null,
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.nav_constructor),
                    style = MaterialTheme.typography.titleLarge
                )
            },
            selected = isSelected == StoveMenus.DESIGNER,
            onClick = navigateDesigner,
            colors = navigationIconColors
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = if(isSelected == StoveMenus.PROFILE)
                        painterResource(R.drawable.profile_active)
                    else
                        painterResource(R.drawable.profile_passive),
                    contentDescription = null,
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.nav_profile),
                    style = MaterialTheme.typography.titleLarge
                )
            },
            selected = isSelected == StoveMenus.PROFILE,
            onClick = navigateProfile,
            colors = navigationIconColors
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoveTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {  }
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        },
        navigationIcon = {
            if(canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_backward_icon),
                        contentDescription = stringResource(R.string.arrow_backward_description),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(dimensionResource(R.dimen.icon_size))
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = Modifier.statusBarsPadding()
    )
}