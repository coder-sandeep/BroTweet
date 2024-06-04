package com.codersandeep.brotweet.models

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("first_name" ) var firstName : String? = null,
    @SerializedName("last_name"  ) var lastName  : String? = null,
    @SerializedName("email"      ) var email     : String? = null,
    @SerializedName("password"   ) var password  : String? = null
)
