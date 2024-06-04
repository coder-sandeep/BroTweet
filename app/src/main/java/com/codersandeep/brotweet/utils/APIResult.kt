package com.codersandeep.brotweet.utils

sealed class APIResult<out T> {
    object Loading : APIResult<Nothing>()
    data class Success<out T>(val data: T) : APIResult<T>()
    data class Error(val message: String) : APIResult<Nothing>()
}