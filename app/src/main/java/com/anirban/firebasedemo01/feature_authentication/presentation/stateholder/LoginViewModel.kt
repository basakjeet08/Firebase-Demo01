package com.anirban.firebasedemo01.feature_authentication.presentation.stateholder

import android.util.Log.d
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModel
import com.anirban.firebasedemo01.feature_authentication.presentation.util.LoginState
import com.anirban.firebasedemo01.feature_authentication.presentation.screens.LoginScreen

/**
 * This is the Login Screen [LoginScreen]'s State holder or viewModel Class which feeds Data
 * and state to the [LoginScreen] UI layer
 *
 * @property myRepository Repository Variable
 * @property userInputEmail maintains the value of the Phone Number of the User
 * @property userInputPassword maintains the value of the Password of the User
 * @property showPassword maintains whether the App shows the Password Typed or not
 * @property loginState keeps a track of the Current State of the Login API Request
 *
 * @property changeUserInputEmail updates the email inputted by the User
 * @property changeUserInputPassword updates the password inputted by the User
 * @property passwordShowState This Function returns whether the password will be shown to the User or not
 * @property changePasswordHideStatus updates whether to hide the password or show it
 * @property clearUserInputEmail clears the User Input email
 * @property resetToDefault Resets all the variable values to default
 * @property sendFirebaseLoginRequest sends the Login Request to be handled by the Repository Layer
 */

class LoginViewModel : ViewModel() {

    // Making the Repository Variable
//    private val myRepository = Repository()

    // Phone Number which is entered by the User
    var userInputEmail: String by mutableStateOf("")
        private set

    // Password which is entered by the User
    var userInputPassword: String by mutableStateOf("")
        private set

    // User Check to see if the User wants the Password to be visual in the App or not
    var showPassword: Boolean by mutableStateOf(false)
        private set

    // Variable keeps track of the State of the API Request
    var loginState: LoginState by mutableStateOf(LoginState.Initialized)
        private set

    // This Function sets the new Phone Number in the Variable
    fun changeUserInputEmail(newNumber: String) {
        userInputEmail = newNumber
    }

    // This Function sets the new Password in the Variable
    fun changeUserInputPassword(newPassword: String) {
        userInputPassword = newPassword
    }

    // This Function returns whether the password will be shown to the User or not
    fun passwordShowState(): VisualTransformation {
        if (showPassword)
            return VisualTransformation.None
        return PasswordVisualTransformation()
    }

    // This function changes the Visibility of the Password
    fun changePasswordHideStatus() {
        showPassword = !showPassword
    }

    // This Function clears the User Input Phone Number
    fun clearUserInputEmail() {
        userInputEmail = ""
    }

    // This Function resets all the Data to their default values
    fun resetToDefault() {
        userInputEmail = ""
        userInputPassword = ""
        showPassword = false
        loginState = LoginState.Initialized
    }

    // This Function is Executed to send the Login Request to the Backend Server
    fun sendFirebaseLoginRequest() {
        d("Firebase Login ViewModel", "Called")
    }
}