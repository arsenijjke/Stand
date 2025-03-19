package com.stand.navigation.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.stand.navigation.logic.Destination
import com.stand.navigation.logic.NavHost
import com.stand.screens.camera.CameraScreen
import com.stand.screens.home.HomeScreen
import com.stand.screens.home.HomeViewModel
import com.stand.screens.login.LoginScreen
import com.stand.screens.login.LoginViewModel

@Composable
internal fun AppNavHost(
    modifier: Modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
    navController: NavHostController,
    viewModel: HomeViewModel,
) = NavHost(
    modifier = modifier,
    navController = navController,
    startDestination = Destination.NoArgumentsDestination.LoginScreen
) {
    login()
    home(viewModel)
    camera(viewModel)
}

internal fun NavGraphBuilder.login() {
    composable(
        route = Destination.NoArgumentsDestination.LoginScreen(),
    ) {
        val loginViewModel: LoginViewModel = hiltViewModel()
        LoginScreen(
            viewModel = loginViewModel
        )
    }
}

internal fun NavGraphBuilder.home(viewModel: HomeViewModel) {
    composable(
        route = Destination.NoArgumentsDestination.HomeScreen(),
    ) {

        HomeScreen(
            viewModel = viewModel
        )
    }
}

internal fun NavGraphBuilder.camera(viewModel: HomeViewModel) {
    composable(
        route = Destination.NoArgumentsDestination.CameraScreen(),
    ) {
        CameraScreen(
            homeViewModel = viewModel
        )
    }
}