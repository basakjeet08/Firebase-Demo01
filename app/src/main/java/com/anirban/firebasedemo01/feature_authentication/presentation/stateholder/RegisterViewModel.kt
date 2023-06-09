package com.anirban.firebasedemo01.feature_authentication.presentation.stateholder

import android.util.Log.d
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anirban.firebasedemo01.feature_authentication.presentation.util.RegistrationState
import com.anirban.firebasedemo01.feature_authentication.presentation.screens.RegisterScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.launch

/**
 *
 * This is the Register Screen [RegisterScreen]'s State holder or viewModel Class which feeds Data
 * and state to the [RegisterScreen] UI layer
 *
 * @property userInputEmail This contains the Email inputted by the User
 * @property userInputEnterPassword maintains the value of the Password of the User
 * @property userInputReEnterPassword maintains the value of the Re-Entered Password of the User
 * @property showEnterPassword maintains whether the App shows the Password Typed or not
 * @property showReEnterPassword maintains whether the App shows the Re-Entered Password typed or not
 * @property registrationState This contains the Current state of the UI
 *
 * @property updateUserInputEmail This function updates the Email Id entered by the User
 * @property updateEnterPassword This function updates the password entered by the User
 * @property updateReEnterPassword This function updates the Password re-entered by the User
 * @property enterPasswordShowState This function returns the VisualTransformation of the Enter
 * Password
 * @property reEnterPasswordShowState This function returns the VisualTransformation of the
 * Re-Entered Password of the User
 * @property clearUserInputEmail This Function Clears the User Input Email
 * @property changeEnterPasswordStatus This function updates the Password state of the Enter Password Field
 * @property changeReEnterPasswordStatus This function updates the Password state of the re-Enter Password Field
 * @property resetToDefaults This Function resets all their values to default
 * @property sendFirebaseRegisterRequest This Function sends the Register Request to the Backend Server
 */

class RegisterViewModel : ViewModel() {

    // This contains the Email inputted by the User
    var userInputEmail: String by mutableStateOf("")
        private set

    // Variable which stores the Password Entered by the User
    var userInputEnterPassword: String by mutableStateOf("")
        private set

    // Variable which stores the Password Re-Entered by the User
    var userInputReEnterPassword: String by mutableStateOf("")
        private set

    // Variable which records whether to show the Password Entered by the User
    var showEnterPassword: Boolean by mutableStateOf(false)
        private set

    // Variable which records whether to show the Password Re-Entered by the User
    var showReEnterPassword: Boolean by mutableStateOf(false)
        private set

    // Variable keeps track of the State of the API Request
    var registrationState: RegistrationState by mutableStateOf(RegistrationState.Initialized)
        private set

    // Firebase Authentication Instance
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    // This function updates the Email Id entered by the User
    fun updateUserInputEmail(newEmail: String) {
        userInputEmail = newEmail
    }

    // This function updates the password entered by the User
    fun updateEnterPassword(newPass: String) {
        userInputEnterPassword = newPass
    }

    // This function updates the Password re-entered by the User
    fun updateReEnterPassword(newPass: String) {
        userInputReEnterPassword = newPass
    }


    // This function returns the VisualTransformation of the Enter Password
    fun enterPasswordShowState(): VisualTransformation {
        if (showEnterPassword)
            return VisualTransformation.None
        return PasswordVisualTransformation()
    }

    // This function returns the VisualTransformation of the Re-Entered Password of the User
    fun reEnterPasswordShowState(): VisualTransformation {
        if (showReEnterPassword)
            return VisualTransformation.None
        return PasswordVisualTransformation()
    }

    fun clearUserInputEmail() {
        userInputEmail = ""
    }

    // This function updates the Password state of the Enter Password Field
    fun changeEnterPasswordStatus() {
        showEnterPassword = !showEnterPassword
    }

    // This function updates the Password state of the Re-Enter Password Field
    fun changeReEnterPasswordStatus() {
        showReEnterPassword = !showReEnterPassword
    }

    // This Function resets all their values to default
    fun resetToDefaults() {
        userInputEmail = ""
        userInputEnterPassword = ""
        userInputReEnterPassword = ""

        showEnterPassword = false
        showReEnterPassword = false
        registrationState = RegistrationState.Initialized
    }

    // This Function sends the Register Request to the Backend Server
    fun sendFirebaseRegisterRequest() {

        registrationState = RegistrationState.Loading

        if (userInputEmail.isEmpty() || userInputEnterPassword.isEmpty() || userInputReEnterPassword.isEmpty()) {
            registrationState = RegistrationState.Failure(errorMessage = "Enter All the Data")
            return
        }

        if (userInputEnterPassword != userInputReEnterPassword) {
            registrationState = RegistrationState.Failure(errorMessage = "Passwords doesn't Match")
            return
        }

        viewModelScope.launch {

            firebaseAuth.createUserWithEmailAndPassword(userInputEmail, userInputEnterPassword)
                .addOnCompleteListener {

                    if (it.isSuccessful)
                        registrationState = RegistrationState.Success
                    else {

                        registrationState = when (it.exception) {
                            is FirebaseAuthWeakPasswordException -> RegistrationState.Failure(
                                "Password Need at least 6 characters"
                            )
                            is FirebaseAuthUserCollisionException -> RegistrationState.Failure(
                                "User Already Present"
                            )
                            is FirebaseAuthInvalidCredentialsException -> RegistrationState.Failure(
                                "Invalid Credentials"
                            )
                            else -> RegistrationState.Failure(
                                "Network Not Available"
                            )
                        }
                    }
                }
        }
    }
}