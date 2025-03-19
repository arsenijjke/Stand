package com.stand

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import timber.log.Timber

@HiltAndroidApp
class StandApp : Application(), AppCoroutineScopeProvider {

    override val applicationScope: CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default + CoroutineName("Application CoroutineScope"))

    override fun onCreate() {
        super.onCreate()
        initApplicationScope()
        Timber.plant(Timber.DebugTree())
    }

    private fun initApplicationScope() {
        val observer = object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                applicationScope.cancel("Application process was destroyed.")
            }
        }
        ProcessLifecycleOwner.get().lifecycle.addObserver(observer)
    }
}

interface AppCoroutineScopeProvider {
    val applicationScope: CoroutineScope
}
