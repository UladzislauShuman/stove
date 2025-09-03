package com.example.stove.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import com.example.stove.StoveBottomAppBar
import com.example.stove.StoveTopAppBar
import com.example.stove.presentation.model.StoveMenus
import com.example.stove.presentation.ui.AppViewModelProvider
import com.example.stove.presentation.ui.screens.designer.DesignerAddonScreen
import com.example.stove.presentation.ui.screens.designer.DesignerComponentScreen
import com.example.stove.presentation.ui.screens.designer.DesignerEntryDestination
import com.example.stove.presentation.ui.screens.designer.DesignerEntryScreen
import com.example.stove.presentation.ui.screens.designer.DesignerOptionScreen
import com.example.stove.presentation.ui.screens.designer.DesignerSummaryScreen
import com.example.stove.presentation.ui.screens.designer.DesignerTypeScreen
import com.example.stove.presentation.ui.screens.home.HomeDestination
import com.example.stove.presentation.ui.screens.home.HomeScreen
import com.example.stove.presentation.ui.screens.profile.ProfileDestination
import com.example.stove.presentation.ui.screens.profile.ProfileFavouritesScreen
import com.example.stove.presentation.ui.screens.profile.ProfileScreen
import com.example.stove.presentation.ui.viewmodel.DesignerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/** Добавить DI через Hilt сюда тоже */
@Composable
fun StoveNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var isTransitionRunning by remember { mutableStateOf(false) }
    val currentRoute = navBackStackEntry?.destination?.route

    val topLevelRoutes = setOf(
        "designer_graph/entry",
        "profile_graph/main"
    )

    val screenOrder: Map<String, Int> =
        mapOf(
            "Home" to 0,
            "designer_graph/entry" to 1,
            "designer_graph/type" to 2,
            "designer_graph/component" to 3,
            "designer_graph/option" to 4,
            "designer_graph/addon" to 5,
            "designer_graph/summary" to 6,
            "profile_graph/main" to 7
        )

    Scaffold(
        topBar = {
            when {
                currentRoute?.startsWith("designer_graph/") == true -> {
                    StoveTopAppBar(
                        title = stringResource(DesignerEntryDestination.titleRes),
                        canNavigateBack = currentRoute !in topLevelRoutes,
                        navigateUp = { navController.navigateUp() }
                    )
                }
                currentRoute?.startsWith("profile_graph/") == true -> {
                    StoveTopAppBar(
                        title = stringResource(ProfileDestination.titleRes),
                        canNavigateBack = currentRoute !in topLevelRoutes,
                        navigateUp = { navController.navigateUp() }
                    )
                }
                else -> {
                    StoveTopAppBar(
                        title = stringResource(HomeDestination.titleRes),
                        canNavigateBack = false
                    )
                }
            }
        },
        bottomBar = {
            StoveBottomAppBar(
                /** Сделать оптимальным переход с графа на граф*/
                navigateHome = {
                    if(!isTransitionRunning && currentRoute != "Home") {
                        isTransitionRunning = true
                        navController.navigate(HomeDestination.route)
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(210)
                            isTransitionRunning = false
                        }
                    }
                },
                navigateDesigner = {
                    if(!isTransitionRunning && currentRoute != "designer_graph/entry")  {
                        isTransitionRunning = true
                        navController.navigate("designer_graph")
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(210)
                            isTransitionRunning = false
                        }
                    }
                },
                navigateProfile = {
                    if(!isTransitionRunning && currentRoute != "profile_graph/main") {
                        isTransitionRunning = true
                        navController.navigate("profile_graph")
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(210)
                            isTransitionRunning = false
                        }
                    }
                },
                modifier = Modifier.height(72.dp),
                isSelected =
                    when {
                        currentRoute == "Home" -> StoveMenus.HOME
                        currentRoute?.startsWith("designer_graph") == true -> StoveMenus.DESIGNER
                        currentRoute?.startsWith("profile_graph") == true-> StoveMenus.PROFILE
                        else -> StoveMenus.HOME
                    }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeDestination.route,
            modifier = modifier.padding(innerPadding),
            enterTransition = {
                val from = screenOrder[initialState.destination.route] ?: 0
                val to = screenOrder[targetState.destination.route] ?: 0
                if(to > from) {
                    slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(200)
                    ) + fadeIn(animationSpec = tween(200) )
                } else {
                    slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(200)
                    )+ fadeIn(animationSpec = tween(200) )
                }
            },
            exitTransition = {
                val from = screenOrder[initialState.destination.route] ?: 0
                val to = screenOrder[targetState.destination.route] ?: 0
                if(to > from) {
                    slideOutHorizontally(
                        targetOffsetX = { -it },
                        animationSpec = tween(200)
                    )+ fadeOut(animationSpec = tween(200) )
                } else {
                    slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(200)
                    ) + fadeOut(animationSpec = tween(200) )
                }
            }
        ) {
            composable(
                route = HomeDestination.route
            ) {
                HomeScreen()
            }
            navigation(startDestination = "designer_graph/entry", route = "designer_graph") {
                composable(
                    route = "designer_graph/entry"
                ) { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry("designer_graph")
                    }
                    val designerViewModel: DesignerViewModel = hiltViewModel(
                        viewModelStoreOwner = parentEntry
                    )
                    DesignerEntryScreen(
                        startDesigner = {
                            designerViewModel.loadTypes()
                            navController.navigate("designer_graph/type") },
                    )
                }
                composable(route = "designer_graph/type") { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry("designer_graph")
                    }
                    val designerViewModel: DesignerViewModel = hiltViewModel(
                        viewModelStoreOwner = parentEntry
                    )
                    DesignerTypeScreen(
                        backBehavior = { navController.navigate("designer_graph/entry") },
                        nextBehavior = {
                            designerViewModel.loadComponents()
                            navController.navigate("designer_graph/component")
                                       },
                        viewModel = designerViewModel
                    )
                }
                composable(route = "designer_graph/component") { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry("designer_graph")
                    }
                    val designerViewModel: DesignerViewModel = hiltViewModel(
                        viewModelStoreOwner = parentEntry
                    )
                    DesignerComponentScreen(
                        backBehavior = {
                            designerViewModel.loadTypes()
                            navController.navigate("designer_graph/type") },
                        nextBehavior = {
                            designerViewModel.loadOptions()
                            navController.navigate("designer_graph/option") },
                        viewModel = designerViewModel
                    )
                }
                composable(route = "designer_graph/option") { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry("designer_graph")
                    }
                    val designerViewModel: DesignerViewModel = hiltViewModel(
                        viewModelStoreOwner = parentEntry
                    )
                    DesignerOptionScreen(
                        backBehavior = {
                            designerViewModel.loadComponents()
                            navController.navigate("designer_graph/component") },
                        nextBehavior = {
                            designerViewModel.loadAddons()
                            navController.navigate("designer_graph/addon") },
                        viewModel = designerViewModel
                    )
                }
                composable(route = "designer_graph/addon") { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry("designer_graph")
                    }
                    val designerViewModel: DesignerViewModel = hiltViewModel(
                        viewModelStoreOwner = parentEntry
                    )
                    DesignerAddonScreen(
                        backBehavior = {
                            designerViewModel.loadOptions()
                            navController.navigate("designer_graph/option") },
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
                        backBehavior = { navController.navigate("designer_graph/component") },
                        nextBehavior = {
                            designerViewModel.addToFavourites()
                            navController.navigate("designer_graph")
                        },
                        viewModel = designerViewModel
                    )
                }
            }
            navigation(startDestination = "profile_graph/main", route = "profile_graph") {
                composable(
                    route = "profile_graph/main"
                ) {
                    ProfileScreen(onClickFavourites = { navController.navigate("profile_graph/favourites") })
                }
                composable(route = "profile_graph/favourites") {
                    ProfileFavouritesScreen()
                }
            }
        }
    }
}