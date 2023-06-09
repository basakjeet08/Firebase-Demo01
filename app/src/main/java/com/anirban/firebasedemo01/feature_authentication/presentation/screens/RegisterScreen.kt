package com.anirban.firebasedemo01.feature_authentication.presentation.screens

import android.content.res.Configuration
import android.util.Log.d
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.anirban.firebasedemo01.core.theme.CustomAppTheme
import com.anirban.firebasedemo01.core.theme.buttonShape
import com.anirban.firebasedemo01.feature_authentication.presentation.components.GradientButton
import com.anirban.firebasedemo01.feature_authentication.presentation.components.TextButtonUI
import com.anirban.firebasedemo01.feature_authentication.presentation.components.UserInputUI
import com.anirban.firebasedemo01.feature_authentication.presentation.navigation.AuthenticationRoutes
import com.anirban.firebasedemo01.feature_authentication.presentation.stateholder.RegisterViewModel
import com.anirban.firebasedemo01.feature_authentication.presentation.util.RegistrationState
import com.anirban.firebasedemo01.R
import com.anirban.firebasedemo01.core.theme.custom_icons.Visibility
import com.anirban.firebasedemo01.feature_authentication.presentation.navigation.AuthenticationRoutes.SignUp

/**
 * This is the Register Screen function which gets loaded when the User needs to Register
 * or sign up and make a new Account
 *
 * For Developers :-
 * The Route id of this function is [SignUp] as defined in the [AuthenticationRoutes] class in
 * presentation layer in Feature_Authentication. The ViewModel of this is [RegisterViewModel]
 * which contains all the Business Logic .
 */

// This is the Preview function of the Login Screen
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreview() {
    CustomAppTheme {
        RegisterScreen(navController = rememberNavController())
    }
}

/**
 * The Main Register Screen of this File which calls all the Other Composable functions and places them
 * @param navController This is the NavController Object which is used to navigate Screens
 * @param modifier  Modifiers is passed to prevent Hardcoding and can be used in multiple occasions
 */
@Composable
fun RegisterScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    // Focus Manager for Input Text Fields
    val focusManager = LocalFocusManager.current
    // ViewModel Class Variable for Storing Data and User UI events
    val myViewModel = viewModel<RegisterViewModel>()
    // Context of the Activity
    val context = LocalContext.current
    // Boolean which stores if there is already a Login Request being processed at the time
    var registrationRequestEmpty = true

    // Checking what to do according to the different States of UI
    when (myViewModel.registrationState) {
        is RegistrationState.Success -> {

            // Resetting the values inside the ViewModel
            myViewModel.resetToDefaults()
            Toast.makeText(context, "Sign Up Successful", Toast.LENGTH_SHORT).show()
            navController.navigate(AuthenticationRoutes.Login.route)
        }
        is RegistrationState.Loading -> {
            registrationRequestEmpty = false
        }
        is RegistrationState.Failure -> {
            Toast.makeText(
                context,
                (myViewModel.registrationState as RegistrationState.Failure).errorMessage,
                Toast.LENGTH_SHORT
            ).show()
        }
        else -> {}
    }


    // Surface Covers the Whole screen and keeps the background color for Better App UI colors
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        // This Column Aligns the UI vertically one after another
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {

            // Spacing of 16 dp
            Spacer(modifier = Modifier.height(16.dp))

            // This Function draws the First UserInput Box for input from User
            // Passing all the Relevant Functions to Draw the UI
            UserInputUI(

                // Label in the OutlinedTextField
                inputFieldLabel = R.string.email,

                // Things the User Inputs
                userInput = myViewModel.userInputEmail,

                // Keyboard Enter Key Operation (changes focus to the next TextField)
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),

                // Keyboard Options to Type from (Currently Only Numbers)
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),

                // It is the Clear Icon to be Showed as a Trailing Icon
                trailingIcon = {
                    if (myViewModel.userInputEmail != "") {
                        IconButton(onClick = { myViewModel.clearUserInputEmail() }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                        }
                    }
                }
            ) {
                myViewModel.updateUserInputEmail(it)
            }

            // Spacing of 16 dp
            Spacer(modifier = Modifier.height(16.dp))

            // This Function draws the Second UserInput Box for input from User
            // Passing the Image and the Company Name to be Drawn in the UI
            UserInputUI(

                // Label in the OutlinedTextField
                inputFieldLabel = R.string.enter_password,

                // Things the User Inputs
                userInput = myViewModel.userInputEnterPassword,

                // Keyboard Enter Key Operation (clears the focus)
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),

                // Keyboard Options to Type from (Currently Password)
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),

                // If we show the User Input to the User or not
                visualTransformation = myViewModel.enterPasswordShowState(),

                // It is the Eye Icon (show Password) to be Showed as a Trailing Icon
                trailingIcon = {
                    IconButton(onClick = { myViewModel.changeEnterPasswordStatus() }) {
                        val visibilityIcon =
                            if (myViewModel.showEnterPassword) Visibility.VisibilityOn else Visibility.VisibilityOff
                        val description =
                            if (myViewModel.showEnterPassword) "Show password" else "Hide password"
                        Icon(imageVector = visibilityIcon, contentDescription = description)
                    }
                }
            ) {
                myViewModel.updateEnterPassword(it)
            }

            // Spacing of 24 dp
            Spacer(modifier = Modifier.height(24.dp))

            // This Function draws the Second UserInput Box for input from User
            // Passing the Image and the Company Name to be Drawn in the UI
            UserInputUI(

                // Label in the OutlinedTextField
                inputFieldLabel = R.string.re_enter_password,

                // Things the User Inputs
                userInput = myViewModel.userInputReEnterPassword,

                // Keyboard Enter Key Operation (clears the focus)
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),

                // Keyboard Options to Type from (Currently Password)
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),

                // If we show the User Input to the User or not
                visualTransformation = myViewModel.reEnterPasswordShowState(),

                // It is the Eye Icon (show Password) to be Showed as a Trailing Icon
                trailingIcon = {
                    IconButton(onClick = { myViewModel.changeReEnterPasswordStatus() }) {
                        val visibilityIcon =
                            if (myViewModel.showReEnterPassword) Visibility.VisibilityOn else Visibility.VisibilityOff
                        val description =
                            if (myViewModel.showReEnterPassword) "Show password" else "Hide password"
                        Icon(imageVector = visibilityIcon, contentDescription = description)
                    }
                }
            ) {
                myViewModel.updateReEnterPassword(it)
            }

            // Spacing of 24 dp
            Spacer(modifier = Modifier.height(24.dp))

            // This Function draws the Button taking the shape and the Text to be shown
            GradientButton(
                buttonShape = buttonShape,
                buttonText = R.string.sign_up
            ) {

                // Checking if already a registration Request is getting processed
                if (registrationRequestEmpty)
                    myViewModel.sendFirebaseRegisterRequest()
                else
                    Toast.makeText(context, "Wait", Toast.LENGTH_SHORT).show()
            }

            // Spacing of 24 dp
            Spacer(modifier = Modifier.height(24.dp))

            // This draws a TextButton with the text we want to show as Argument
            TextButtonUI(textToShow = R.string.already_have_an_account) {

                myViewModel.resetToDefaults()

                // This Executes when we press the TextButton
                navController.navigate(AuthenticationRoutes.Login.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        }
    }
}