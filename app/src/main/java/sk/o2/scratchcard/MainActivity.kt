package sk.o2.scratchcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import sk.o2.scratchcard.data.local.ActivateModel
import sk.o2.scratchcard.data.local.DetailModel
import sk.o2.scratchcard.data.local.HomeModel
import sk.o2.scratchcard.ui.screens.activate.ActivateScreen
import sk.o2.scratchcard.ui.screens.detail.DetailScreen
import sk.o2.scratchcard.ui.screens.home.HomeScreen
import sk.o2.scratchcard.ui.theme.O2ScratchCardTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            )
        )
        setContent {
            O2ScratchCardTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = HomeModel
                ) {
                    composable<HomeModel> {
                        HomeScreen(
                            viewModel = hiltViewModel(),
                            onNavigateToScratch = {
                                navController.navigate(DetailModel)
                            },
                            onNavigateToActivate = {
                                navController.navigate(ActivateModel)
                            }
                        )
                    }

                    composable<DetailModel> {
                        DetailScreen(
                            viewModel = hiltViewModel(),
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }

                    composable<ActivateModel> {
                        ActivateScreen(
                            viewModel = hiltViewModel(),
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}