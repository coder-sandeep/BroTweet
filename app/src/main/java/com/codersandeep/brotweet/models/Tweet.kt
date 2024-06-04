package com.codersandeep.brotweet.models

import com.google.gson.annotations.SerializedName

data class Tweet(
    @SerializedName("tweet") var tweet: String? = null,
    @SerializedName("_id") var Id: String? = null,
    @SerializedName("__v") var _v: Int? = null
)
