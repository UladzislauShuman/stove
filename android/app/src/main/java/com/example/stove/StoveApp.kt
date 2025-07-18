package com.example.stove

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    BottomAppBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier.clickable {
                        navigateHome()
                    }
                )  {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (isSelected.number == 1) {
                            Icon(
                                painter = painterResource(R.drawable.home_active),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        } else {
                            Icon(
                                painter = painterResource(R.drawable.home_passive),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondaryContainer
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
                Box(
                    modifier = Modifier.clickable {
                        navigateDesigner()
                    }
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (isSelected.number == 2) {
                            Icon(
                                painter = painterResource(R.drawable.designer_active),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        } else {
                            Icon(
                                painter = painterResource(R.drawable.designer_passive),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                        Text(
                            text = stringResource(R.string.nav_constructor),
                            style = MaterialTheme.typography.titleLarge,
                            color = if (isSelected.number == 2) MaterialTheme.colorScheme.onPrimaryContainer
                            else MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
                Box(
                    modifier = Modifier.clickable {
                        navigateProfile()
                    }
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (isSelected.number == 3) {
                            Icon(
                                painter = painterResource(R.drawable.profile_active),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        } else {
                            Icon(
                                painter = painterResource(R.drawable.profile_passive),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                        Text(
                            text = stringResource(R.string.nav_profile),
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
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
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