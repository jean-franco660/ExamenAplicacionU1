package pe.edu.upeu.ExamenUnidad1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import pe.edu.upeu.ExamenUnidad1.ViewModel.ResultViewModel
import pe.edu.upeu.ExamenUnidad1.ui.navigation.MyAppRoute
import pe.edu.upeu.ExamenUnidad1.ui.navigation.MyAppTopLevelDestination
import pe.edu.upeu.ExamenUnidad1.ui.navigation.RouteActions
import pe.edu.upeu.ExamenUnidad1.ui.navigation.TOP_LEVEL_DESTINATIONS
import pe.edu.upeu.ExamenUnidad1.ui.presentation.screens.Board
import pe.edu.upeu.ExamenUnidad1.ui.presentation.screens.Game
import pe.edu.upeu.ExamenUnidad1.ui.presentation.screens.Home
import pe.edu.upeu.ExamenUnidad1.ui.theme.ExamenUnidad1Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenUnidad1Theme{
                val navController = rememberNavController()
                val navigateAction = remember(navController) {
                    RouteActions(navController)
                }
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val selectedDestination = navBackStackEntry?.destination?.route ?: MyAppRoute.HOME
                val viewModel by viewModels<ResultViewModel>()
                MyAppContent(
                    navController = navController,
                    selectedDestination = selectedDestination,
                    navigateTopLevelDestination = navigateAction::navigateTo
                )
            }
        }
    }
}

@Composable
fun MyAppContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    selectedDestination: String,
    navigateTopLevelDestination: (MyAppTopLevelDestination) -> Unit
) {
    val viewModel: ResultViewModel = viewModel()
    Row(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            NavHost(
                modifier = Modifier.weight(1f),
                navController = navController,
                startDestination = MyAppRoute.HOME
            ) {
                composable(MyAppRoute.HOME) {
                    Home(navController = navController)
                }
                composable(MyAppRoute.GAME) {
                    Game(navController = navController, viewModel = viewModel )
                }
                composable(MyAppRoute.BOARD) {
                    viewModel.getResults()
                    Board(viewModel.resultsListApi, viewModel)
                }
            }
            MyAppBottomNavigation(
                selectedDestination = selectedDestination,
                navigateTopLevelDestination = navigateTopLevelDestination
            )
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppTopBar(){
    TopAppBar(
        title = {
            Text(
                text = "Top",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

    )
}

@Composable
fun MyAppBottomNavigation(
    selectedDestination: String,
    navigateTopLevelDestination: (MyAppTopLevelDestination) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TOP_LEVEL_DESTINATIONS.forEach { destination ->
            Column(
                modifier = Modifier
                    .clickable { navigateTopLevelDestination(destination) }
                    .padding(10.dp)
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = destination.selectedIcon,
                    contentDescription = null,
                    tint = if (selectedDestination == destination.route) {
                        Color(0xFF6482E2)
                    } else {
                        Color.Gray
                    }
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = stringResource(id = destination.iconTextId),
                    color = if (selectedDestination == destination.route) {
                        Color(0xFF6482E2)
                    } else {
                        Color.Gray
                    }
                )
            }
        }
    }
}