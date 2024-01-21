package com.gzaber.forexviewer.ui.navigation

import androidx.navigation.NavController
import com.gzaber.forexviewer.ui.navigation.ForexViewerDestinationArgs.SYMBOL_ARG
import com.gzaber.forexviewer.ui.navigation.ForexViewerScreens.CHART_SCREEN
import com.gzaber.forexviewer.ui.navigation.ForexViewerScreens.FAVORITES_SCREEN
import com.gzaber.forexviewer.ui.navigation.ForexViewerScreens.FOREX_PAIRS_SCREEN

private object ForexViewerScreens {
    const val FAVORITES_SCREEN = "favorites"
    const val CHART_SCREEN = "chart"
    const val FOREX_PAIRS_SCREEN = "forexPairs"
}

object ForexViewerDestinationArgs {
    const val SYMBOL_ARG = "symbol"
}

object ForexViewerDestinations {
    const val FAVORITES_ROUTE = FAVORITES_SCREEN
    const val CHART_ROUTE = "$CHART_SCREEN/{$SYMBOL_ARG}"
    const val FOREX_PAIRS_ROUTE = FOREX_PAIRS_SCREEN
}

class ForexViewerNavigationActions(
    private val navController: NavController
) {
    fun navigateToChart(symbol: String) {
        navController.navigate("$CHART_SCREEN/${symbol.replace("/", "_")}")
    }

    fun navigateToForexPairs() {
        navController.navigate(FOREX_PAIRS_SCREEN)
    }
}