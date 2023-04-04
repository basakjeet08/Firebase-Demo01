package com.anirban.firebasedemo01.feature_authentication.presentation.util


/**
 * This sealed Class contains all the States of the Registration Request in a API
 *
 * @property Initialized is used to define the Initial State
 * @property Loading is used to define the state of the API call when it is in fetching Phase
 * @property Success is used to define when the API call is a Success
 * @property Failure is used to define when the API call is a Failure
 */
sealed class RegistrationState{
    object Initialized : RegistrationState()
    object Loading : RegistrationState()
    object Success : RegistrationState()
    class Failure(val errorMessage : String) : RegistrationState()
}