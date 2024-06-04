package com.codersandeep.brotweet.di

import com.codersandeep.brotweet.api.TweetsAPI
import com.codersandeep.brotweet.api.UserAPI
import com.codersandeep.brotweet.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesUserAPI(retrofit: Retrofit): UserAPI {
        return retrofit.create(UserAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesTweetsAPI(retrofit: Retrofit): TweetsAPI {
        return retrofit.create(TweetsAPI::class.java)
    }
}