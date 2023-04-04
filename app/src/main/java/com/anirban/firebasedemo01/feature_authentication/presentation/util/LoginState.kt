package com.anirban.firebasedemo01.feature_authentication.presentation.util

sealed class LoginState {
    object Initialized : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    class Failure(val errorMessage: String) : LoginState()
}