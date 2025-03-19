package com.stand.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    viewModel: LoginViewModel
) {
    Login(
        navigateToHomeScreen = { viewModel.navigateToHomeScreen() },
        loginWithGoogle = { viewModel.loginWithGoogle() }
    )
}

@Composable
fun Login(
    modifier: Modifier = Modifier,
    navigateToHomeScreen: () -> Unit,
    loginWithGoogle: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = modifier.width(200.dp),
            onClick = navigateToHomeScreen ) {
            Text("Log in with Yandex")
        }

        Spacer(modifier = modifier.height(10.dp))

        Button(
            modifier = modifier.width(200.dp),
            onClick = navigateToHomeScreen
        ) {
            Text("Log in with Instagram")
        }

        Spacer(modifier = modifier.height(10.dp))

        Button(
            modifier = modifier.width(200.dp),
            onClick = loginWithGoogle
        ) {
            Text("Log in with Google")
        }
    }
}
