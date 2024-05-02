package pe.edu.upeu.ExamenUnidad1.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import pe.edu.upeu.ExamenUnidad1.R

class RouteActions(private val navController: NavHostController) {
    fun navigateTo(destination: MyAppTopLevelDestination) {
        navController.navigate(destination.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
        }
    }
}

data class MyAppTopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val iconTextId: Int
)

val TOP_LEVEL_DESTINATIONS = listOf(
    MyAppTopLevelDestination(
        route = MyAppRoute.HOME,
        selectedIcon = Icons.Default.Home,
        iconTextId = R.string.home
    ),
    MyAppTopLevelDestination(
        route = MyAppRoute.GAME,
        selectedIcon = Icons.Default.PlayArrow,
        iconTextId = R.string.account
    ),
    MyAppTopLevelDestination(
        route = MyAppRoute.BOARD,
        selectedIcon = Icons.Default.DateRange,
        iconTextId = R.string.setting
    ),
)

object MyAppRoute {
    const val HOME = "home"
    const val GAME = "game"
    const val BOARD = "board"
}