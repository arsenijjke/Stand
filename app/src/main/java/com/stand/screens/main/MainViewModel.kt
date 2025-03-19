package com.stand.screens.main

import androidx.lifecycle.ViewModel
import com.stand.navigation.logic.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    navigator: Navigator
) : ViewModel() {

    private val _state = MutableStateFlow(
        MainActivityState(
            navigationChannel = navigator.navigationChannel,
        )
    )
    val state = _state.asStateFlow()

}