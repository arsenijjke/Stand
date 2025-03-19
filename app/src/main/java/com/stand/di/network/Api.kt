package com.stand.di.network

import com.stand.model.AuthRequest
import com.stand.model.AuthResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @POST("auth/google-login")
    suspend fun loginWithGoogle(): AuthResponse

    @POST("auth/yandex-login")
    suspend fun loginWithYandex(@Body body: AuthRequest): AuthResponse
}
