package com.codersandeep.brotweet.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codersandeep.brotweet.api.UserAPI
import com.codersandeep.brotweet.models.LoginRegisterResponse
import com.codersandeep.brotweet.models.LoginRequest
import com.codersandeep.brotweet.models.RegisterRequest
import com.codersandeep.brotweet.models.WelcomeResponse
import com.codersandeep.brotweet.utils.APIResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class SingInViewModel @Inject constructor(private val userAPI: UserAPI) : ViewModel() {
    private var _registerResponse = MutableLiveData<APIResult<LoginRegisterResponse>>()
    val registerResponse: LiveData<APIResult<LoginRegisterResponse>>
        get() = _registerResponse

    private var _welcomeResponse = MutableLiveData<APIResult<WelcomeResponse>>()
    val welcomeResponse: LiveData<APIResult<WelcomeResponse>>
        get() = _welcomeResponse

    private var _loginResponse = MutableLiveData<APIResult<LoginRegisterResponse>>()
    val loginResponse: LiveData<APIResult<LoginRegisterResponse>>
        get() = _loginResponse

    fun register(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            _registerResponse.postValue(APIResult.Loading)
            try {
                val result = userAPI.register(registerRequest)

                if (result.isSuccessful && result.body() != null) {
                    _registerResponse.postValue(APIResult.Success(result.body()!!))
                } else if (result.code() == 409) {
                    _registerResponse.postValue(APIResult.Success(result.body()!!))
                } else _registerResponse.postValue(APIResult.Error("Something went wrong!!"))
            } catch (e: Exception) {
                _registerResponse.postValue(
                    APIResult.Error(
                        e.message ?: "An unknown error occurred"
                    )
                )
            }
        }
    }

    fun login(loginRequest: LoginRequest) {
        Log.d("TAG", "login: ${loginRequest.email}")
        viewModelScope.launch {
            _loginResponse.postValue(APIResult.Loading)
            try {
                val result = userAPI.login(loginRequest)
                Log.d("TAG", "login: ${result.body()}")
                if (result.isSuccessful && result.body() != null) {
                    _loginResponse.postValue(APIResult.Success(result.body()!!))
                } else if (result.code() == 400) _loginResponse.postValue(APIResult.Error("Invalid Credentials!"))
                else _loginResponse.postValue(APIResult.Error("Something went wrong!!"))
            } catch (e: Exception) {
                _registerResponse.postValue(
                    APIResult.Error(
                        e.message ?: "An unknown error occurred"
                    )
                )
            }
        }
    }

    fun welcome(key: String) {
        viewModelScope.launch {
            try {
                _welcomeResponse.postValue(APIResult.Loading)
                val result = userAPI.welcome(key)
                if (result.isSuccessful && result.body() != null) {
                    _welcomeResponse.postValue(APIResult.Success(result.body()!!))
                } else {
                    _welcomeResponse.postValue(APIResult.Error("Something went wrong!!"))
                }
            } catch (e: Exception) {
                _registerResponse.postValue(
                    APIResult.Error(
                        e.message ?: "An unknown error occurred"
                    )
                )
            }
        }
    }
}