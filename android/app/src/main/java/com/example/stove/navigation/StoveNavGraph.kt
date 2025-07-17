package com.example.stove.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.stove.StoveBottomAppBar
import com.example.stove.StoveTopAppBar
import com.example.stove.model.StoveMenus
import com.example.stove.ui.screens.DesignerEntryDestination
import com.example.stove.ui.screens.DesignerEntryScreen
import com.example.stove.ui.screens.HomeDestination
import com.example.stove.ui.screens.HomeScreen
import com.example.stove.ui.screens.ProfileDestination
import com.example.stove.ui.screens.ProfileScreen

@Composable
fun StoveNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            when(currentRoute) {
                "Home" -> {
                    StoveTopAppBar(
                        title = stringResource(HomeDestination.titleRes),
                        canNavigateBack = false
                    )
                }
                "Profile" -> {
                    StoveTopAppBar(
                        title = stringResource(ProfileDestination.titleRes),
                        canNavigateBack = false
                    )
                }
                else -> {
                    StoveTopAppBar(
                        title = stringResource(DesignerEntryDestination.titleRes),
                        canNavigateBack = false
                    )
                }
            }
        },
        bottomBar = {
            StoveBottomAppBar(
                navController = navController,
                modifier = Modifier,
                isSelected =
                    when(currentRoute) {
                        "Home" -> StoveMenus.HOME
                        "DesignerEntry" -> StoveMenus.DESIGNER
                        "Profile" -> StoveMenus.PROFILE
                        else -> StoveMenus.HOME
                    }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeDestination.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = HomeDestination.route) {
                HomeScreen()
            }
            composable(route = ProfileDestination.route) {
                ProfileScreen()
            }
            composable(route = DesignerEntryDestination.route) {
                DesignerEntryScreen()
            }
        }
    }
}