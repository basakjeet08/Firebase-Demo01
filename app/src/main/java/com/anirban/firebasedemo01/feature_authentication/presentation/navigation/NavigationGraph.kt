package com.anirban.firebasedemo01.feature_authentication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.anirban.firebasedemo01.feature_authentication.presentation.screens.RegisterScreen
import com.anirban.firebasedemo01.feature_authentication.presentation.screens.LoginScreen

/**
 * Navigation Graph : It contains all the Different Routes in the Authentication Feature
 */
@Composable
fun AuthenticationNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = AuthenticationRoutes.Login.route,
        builder = {
            composable(
                AuthenticationRoutes.Login.route,
                content = { LoginScreen(navController = navController) })
            composable(
                AuthenticationRoutes.SignUp.route,
                content = { RegisterScreen(navController = navController) })
        })
}