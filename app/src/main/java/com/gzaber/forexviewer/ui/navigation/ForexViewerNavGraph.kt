package com.gzaber.forexviewer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gzaber.forexviewer.ui.chart.ChartScreen
import com.gzaber.forexviewer.ui.favorites.FavoritesScreen
import com.gzaber.forexviewer.ui.forexpairs.ForexPairsScreen
import com.gzaber.forexviewer.ui.navigation.ForexViewerDestinationArgs.SYMBOL_ARG

@Composable
fun ForexViewerNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ForexViewerDestinations.FAVORITES_ROUTE,
    navActions: ForexViewerNavigationActions = remember(navController) {
        ForexViewerNavigationActions(navController)
    }
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = ForexViewerDestinations.FAVORITES_ROUTE
        ) {
            FavoritesScreen(
                onForexPairsClick = {
                    navActions.navigateToForexPairs()
                },
                onListItemClick = {
                    navActions.navigateToChart(it)
                }
            )
        }

        composable(
            route = ForexViewerDestinations.CHART_ROUTE,
            arguments = listOf(
                navArgument(SYMBOL_ARG) { type = NavType.StringType }
            )
        ) {
            ChartScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = ForexViewerDestinations.FOREX_PAIRS_ROUTE
        ) {
            ForexPairsScreen(
                onBackClick = { navController.popBackStack() },
                onListItemClick = {
                    navActions.navigateToChart(it)
                }
            )
        }
    }
}