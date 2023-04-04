package com.anirban.firebasedemo01.feature_authentication.presentation.navigation

/**
 * This class contains the Several Routes that can be taken for navigation
 *
 * @property Login this is the route to the login screen
 * @property SignUp this is the route to the Register Screen
 */
sealed class AuthenticationRoutes(val route: String) {
    object Login : AuthenticationRoutes("login-screen")
    object SignUp : AuthenticationRoutes("signup-screen")
}