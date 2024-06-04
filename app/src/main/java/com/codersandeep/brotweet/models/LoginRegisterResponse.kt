package com.codersandeep.brotweet.models

import com.google.gson.annotations.SerializedName

data class LoginRegisterResponse (
    @SerializedName("first_name" ) var firstName : String? = null,
    @SerializedName("last_name"  ) var lastName  : String? = null,
    @SerializedName("_id"        ) var Id        : String? = null,
    @SerializedName("email"      ) var email     : String? = null,
    @SerializedName("token"      ) var token     : String? = null
)