package com.stand.screens.main

import com.stand.navigation.logic.NavigationIntent
import kotlinx.coroutines.channels.Channel

data class MainActivityState(
    val navigationChannel: Channel<NavigationIntent>,
)
