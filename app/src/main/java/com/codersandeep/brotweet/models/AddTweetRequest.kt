package com.codersandeep.brotweet.models

import com.google.gson.annotations.SerializedName

data class AddTweetRequest(
    @SerializedName("tweet") var tweet: String? = null
)
