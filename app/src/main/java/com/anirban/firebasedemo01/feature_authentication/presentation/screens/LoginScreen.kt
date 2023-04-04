package com.anirban.firebasedemo01.feature_authentication.presentation.screens

import android.content.res.Configuration
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
import androidx.compose.ui.Alignment
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
import com.anirban.firebasedemo01.feature_authentication.presentation.components.UserInputUI
import com.anirban.firebasedemo01.core.theme.CustomAppTheme
import com.anirban.firebasedemo01.R
import com.anirban.firebasedemo01.core.theme.buttonShape
import com.anirban.firebasedemo01.core.theme.custom_icons.Visibility
import com.anirban.firebasedemo01.feature_authentication.presentation.components.GradientButton
import com.anirban.firebasedemo01.feature_authentication.presentation.components.TextButtonUI
import com.anirban.firebasedemo01.feature_authentication.presentation.navigation.AuthenticationRoutes
import com.anirban.firebasedemo01.feature_authentication.presentation.stateholder.LoginViewModel
import com.anirban.firebasedemo01.feature_authentication.presentation.util.LoginState

// This is the Preview function of the Screen when Loading
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun DefaultPreviewLoading() {
    CustomAppTheme {
        LoginScreen(
            navController = rememberNavController()
        )
    }
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Surface(
        modifier = modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Focus Manager for Input Text Fields
            val focusManager = LocalFocusManager.current
            // ViewModel Variable
            val myViewModel: LoginViewModel = viewModel()
            // Context of the Activity
            val context = LocalContext.current
            // Boolean which stores if there is already a Login Request being processed at the time
            var loginRequestEmpty = true

            // Checking what to do according to the different States of UI
            when (myViewModel.loginState) {
                is LoginState.Success -> {

                    // Starting the New Activity
//                    context.startActivity(Intent(context, HomeActivity::class.java))
//                    (context as Activity).finish()

                }
                is LoginState.Loading -> {
                    loginRequestEmpty = false
                }
                is LoginState.Failure -> {
                    Toast.makeText(
                        context,
                        (myViewModel.loginState as LoginState.Failure).errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {}
            }

            // Email User Input TextInput Bar
            UserInputUI(

                // Label in the OutlinedTextField
                inputFieldLabel = R.string.email,

                // Things the User Inputs
                userInput = myViewModel.userInputEmail,

                // Keyboard Enter Key Operation (clears the focus)
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),

                // Keyboard Options to Type from (Currently Password)
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
                myViewModel.changeUserInputEmail(it)
            }

            // Spacing of 24 dp
            Spacer(modifier = Modifier.height(24.dp))

            // Password TextInput Bar
            UserInputUI(

                // Label in the OutlinedTextField
                inputFieldLabel = R.string.enter_password,

                // Things the User Inputs
                userInput = myViewModel.userInputPassword,

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
                visualTransformation = myViewModel.passwordShowState(),

                // It is the Eye Icon (show Password) to be Showed as a Trailing Icon
                trailingIcon = {
                    IconButton(onClick = { myViewModel.changePasswordHideStatus() }) {
                        val visibilityIcon =
                            if (myViewModel.showPassword) Visibility.VisibilityOn else Visibility.VisibilityOff
                        val description =
                            if (myViewModel.showPassword) "Show password" else "Hide password"
                        Icon(imageVector = visibilityIcon, contentDescription = description)
                    }
                }
            ) {
                myViewModel.changeUserInputPassword(it)
            }

            // Spacing of 24 dp
            Spacer(modifier = Modifier.height(24.dp))

            // This Function draws the Button taking the shape and the Text to be shown
            GradientButton(
                buttonShape = buttonShape,
                buttonText = R.string.login
            ) {

                // Checking if already a login Request is getting processed
                if (loginRequestEmpty)
                    myViewModel.sendFirebaseLoginRequest()
                else
                    Toast.makeText(context, "Wait", Toast.LENGTH_SHORT).show()
            }

            // Spacing of 24 dp
            Spacer(modifier = Modifier.height(24.dp))

            // This draws a TextButton with the text we want to show as Argument
            TextButtonUI(textToShow = R.string.create_an_account) {

                myViewModel.resetToDefault()

                // This Executes when we press the TextButton
                navController.navigate(AuthenticationRoutes.SignUp.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        }
    }
}