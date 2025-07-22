package com.example.stove.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import com.example.stove.StoveBottomAppBar
import com.example.stove.StoveTopAppBar
import com.example.stove.model.StoveMenus
import com.example.stove.ui.AppViewModelProvider
import com.example.stove.ui.screens.designer.DesignerEntryDestination
import com.example.stove.ui.screens.designer.DesignerEntryScreen
import com.example.stove.ui.screens.designer.DesignerMaterialScreen
import com.example.stove.ui.screens.designer.DesignerSummaryDestination
import com.example.stove.ui.screens.designer.DesignerSummaryScreen
import com.example.stove.ui.screens.designer.DesignerTypeScreen
import com.example.stove.ui.screens.designer.DesignerViewModel
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
                    when {
                        currentRoute == "Home" -> StoveMenus.HOME
                        currentRoute?.startsWith("designer_graph") == true -> StoveMenus.DESIGNER
                        currentRoute == "Profile" -> StoveMenus.PROFILE
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

            navigation(startDestination = "designer_graph/entry", route = "designer_graph") {

                composable(route = "designer_graph/entry") {
                    DesignerEntryScreen(
                        startDesigner = { navController.navigate("designer_graph/type") },
                    )
                }
                composable(route = "designer_graph/type") { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry("designer_graph")
                    }
                    val designerViewModel: DesignerViewModel = viewModel(parentEntry, factory = AppViewModelProvider.Factory)

                    DesignerTypeScreen(
                        backBehavior = { navController.navigate("designer_graph/entry") },
                        nextBehavior = { navController.navigate("designer_graph/material") },
                        viewModel = designerViewModel
                    )
                }
                composable(route = "designer_graph/material") { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry("designer_graph")
                    }
                    val designerViewModel: DesignerViewModel = viewModel(parentEntry, factory = AppViewModelProvider.Factory)

                    DesignerMaterialScreen(
                        backBehavior = { navController.navigate("designer_graph/type") },
                        nextBehavior = { navController.navigate("designer_graph/summary") },
                        viewModel = designerViewModel
                    )
                }
                composable(route = "designer_graph/summary") { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry("designer_graph")
                    }
                    val designerViewModel: DesignerViewModel = viewModel(parentEntry, factory = AppViewModelProvider.Factory)

                    DesignerSummaryScreen (
                        backBehavior = { navController.navigate("designer_graph/material") },
                        nextBehavior = {
                            designerViewModel.addToFavourites()
                            navController.navigate("designer_graph")
                        },
                        viewModel = designerViewModel
                    )
                }
            }

            composable(route = HomeDestination.route) {
                HomeScreen()
            }


            navigation(startDestination = "profile", route = "profile_graph") {
                composable(route = "profile") {
                    ProfileScreen()
                }
            }
        }
    }
}