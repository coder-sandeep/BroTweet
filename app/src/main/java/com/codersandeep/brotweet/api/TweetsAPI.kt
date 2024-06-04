package com.codersandeep.brotweet.api

import com.codersandeep.brotweet.models.AddTweetRequest
import com.codersandeep.brotweet.models.Tweet
import com.codersandeep.brotweet.models.AddTweetResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface TweetsAPI {
    @GET("/tweets")
    suspend fun getAllTweets(@Header("x-api-key") key: String): Response<List<Tweet>>

    @POST("/tweets")
    suspend fun addTweet(@Header("x-api-key") key: String, @Body addTweetRequest: AddTweetRequest): Response<AddTweetResponse>
}