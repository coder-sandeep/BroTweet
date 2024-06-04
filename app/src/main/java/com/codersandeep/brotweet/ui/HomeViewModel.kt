package com.codersandeep.brotweet.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codersandeep.brotweet.api.TweetsAPI
import com.codersandeep.brotweet.models.AddTweetRequest
import com.codersandeep.brotweet.models.Tweet
import com.codersandeep.brotweet.models.AddTweetResponse
import com.codersandeep.brotweet.utils.APIResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val tweetsAPI: TweetsAPI) : ViewModel() {
    private var _tweetsResponse = MutableLiveData<APIResult<List<Tweet>>>()
    val tweetsResponse: LiveData<APIResult<List<Tweet>>>
        get() = _tweetsResponse

    private var _addTweetResponse = MutableLiveData<APIResult<AddTweetResponse>>()
    val addTweetResponse: LiveData<APIResult<AddTweetResponse>>
        get() = _addTweetResponse

    fun getTweets(key: String) {
        viewModelScope.launch {
            _tweetsResponse.postValue(APIResult.Loading)
            try {
                val result = tweetsAPI.getAllTweets(key)
                Log.d("ablog", result.body().toString())
                if (result.isSuccessful && result.body() != null) {
                    _tweetsResponse.postValue(APIResult.Success(result.body()!!))
                } else {
                    _tweetsResponse.postValue(APIResult.Error("Something went wrong!!"))
                }
            } catch (e: Exception) {
                _tweetsResponse.postValue(
                    APIResult.Error(
                        e.message ?: "An unknown error occurred"
                    )
                )
            }
        }
    }

    fun addTweet(addTweetRequest: AddTweetRequest, key: String) {
        _addTweetResponse.postValue(APIResult.Loading)
        viewModelScope.launch {
            try {
                val result = tweetsAPI.addTweet(key, addTweetRequest)
                Log.d("ablog", result.body().toString())
                if (result.isSuccessful && result.body() != null) {
                    _addTweetResponse.postValue(APIResult.Success(result.body()!!))
                } else {
                    _addTweetResponse.postValue(APIResult.Error("Something went wrong!!"))
                }
            } catch (e: Exception) {
                _addTweetResponse.postValue(
                    APIResult.Error(
                        e.message ?: "An unknown error occurred"
                    )
                )
            }
        }
    }
}