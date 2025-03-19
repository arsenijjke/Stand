package com.stand.di.navigator

import com.stand.navigation.logic.Navigator
import com.stand.navigation.logic.NavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @Singleton
    @Binds
    fun bindAppNavigator(appNavigator: NavigatorImpl): Navigator
}