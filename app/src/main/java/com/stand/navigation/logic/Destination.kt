package com.stand.navigation.logic

sealed class Destination(protected val route: String, vararg params: String) {
    val fullRoute: String = if (params.isEmpty()) route else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/{${it}}") }
        builder.toString()
    }

    sealed class NoArgumentsDestination(route: String) : Destination(route) {
        operator fun invoke(): String = route
        object LoginScreen : NoArgumentsDestination(LOGIN_SCREEN)
        object HomeScreen : NoArgumentsDestination(HOME_SCREEN)
        object CameraScreen : NoArgumentsDestination(CAMERA_SCREEN)
    }

    companion object {
        private const val LOGIN_SCREEN = "login_screen"
        private const val HOME_SCREEN = "home_screen"
        private const val CAMERA_SCREEN = "camera_screen"
    }
}

internal fun String.appendParams(vararg params: Pair<String, Any?>): String {
    val builder = StringBuilder(this)

    params.forEach {
        it.second?.toString()?.let { arg ->
            builder.append("/$arg")
        }
    }

    return builder.toString()
}