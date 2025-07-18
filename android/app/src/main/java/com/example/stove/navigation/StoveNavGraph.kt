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
import androidx.navigation.navigation
import com.example.stove.StoveBottomAppBar
import com.example.stove.StoveTopAppBar
import com.example.stove.model.StoveMenus
import com.example.stove.ui.screens.designer.DesignerEntryDestination
import com.example.stove.ui.screens.designer.DesignerEntryScreen
import com.example.stove.ui.screens.designer.DesignerMaterialDestination
import com.example.stove.ui.screens.designer.DesignerMaterialScreen
import com.example.stove.ui.screens.designer.DesignerTypeDestination
import com.example.stove.ui.screens.designer.DesignerTypeScreen
import com.example.stove.ui.screens.home.HomeDestination
import com.example.stove.ui.screens.home.HomeScreen
import com.example.stove.ui.screens.profile.ProfileDestination
import com.example.stove.ui.screens.profile.ProfileScreen

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

                /** Сделать оптимальным переход с графа на граф*/

                navigateHome = {
                    navController.navigate(HomeDestination.route)
                },
                navigateDesigner = {
                    navController.navigate("designer_graph")
                },
                navigateProfile = {
                    navController.navigate(ProfileDestination.route)
                },
                modifier = Modifier,
                isSelected =
                    when(currentRoute) {
                        "Home" -> StoveMenus.HOME
                        "designer_graph" -> StoveMenus.DESIGNER
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

            navigation(startDestination = DesignerEntryDestination.route, route = "designer_graph") {
                composable(route = DesignerEntryDestination.route) {
                    DesignerEntryScreen(
                        startDesigner = { navController.navigate(DesignerTypeDestination.route) }
                    )
                }
                composable(route = DesignerTypeDestination.route) {
                    DesignerTypeScreen()
                }
                composable(route = DesignerMaterialDestination.route) {
                    DesignerMaterialScreen()
                }
            }
        }
    }
}