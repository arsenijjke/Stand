package com.stand.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stand.di.network.Api
import com.stand.model.AuthRequest
import com.stand.navigation.logic.Destination
import com.stand.navigation.logic.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val navigator: Navigator,
    val api: Api,
) : ViewModel() {

    fun navigateToHomeScreen() {
        navigator.tryNavigateTo(Destination.NoArgumentsDestination.HomeScreen())
    }

    fun loginWithGoogle() {
        viewModelScope.launch {
            api.loginWithGoogle()
        }
    }
}
