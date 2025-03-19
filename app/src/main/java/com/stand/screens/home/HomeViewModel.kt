package com.stand.screens.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.stand.navigation.logic.Destination
import com.stand.navigation.logic.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val navigator: Navigator
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    fun setImageUri(uri: Uri?) {
        _homeState.update {
            _homeState.value.copy(imageUri = uri)
        }
    }

    fun navigateToCamera() {
        navigator.tryNavigateTo(Destination.NoArgumentsDestination.CameraScreen())
    }

    fun navigateBackToHome(uri: Uri) {
        setImageUri(uri)
        navigator.tryNavigateBack()
    }
}
