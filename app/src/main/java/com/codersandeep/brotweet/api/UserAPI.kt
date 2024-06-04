package com.codersandeep.brotweet.api

import com.codersandeep.brotweet.models.RegisterRequest
import com.codersandeep.brotweet.models.LoginRegisterResponse
import com.codersandeep.brotweet.models.LoginRequest
import com.codersandeep.brotweet.models.WelcomeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserAPI {
    @POST("/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<LoginRegisterResponse>

    @GET("/welcome")
    suspend fun welcome(@Header("x-api-key") key: String): Response<WelcomeResponse>

    @POST("/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginRegisterResponse>

//    @GET("/products/{productId}")
//    suspend fun getSingleProduct(@Path("productId") id: Int): Response<ProductsItem>

    //@BODY for POST
}