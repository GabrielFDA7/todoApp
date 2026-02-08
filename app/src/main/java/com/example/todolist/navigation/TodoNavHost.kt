package com.example.todolist.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.todolist.data.auth.AuthRepositoryImpl
import com.example.todolist.ui.feature.addedit.AddEditScreen
import com.example.todolist.ui.feature.list.ListScreen
import com.example.todolist.ui.feature.login.LoginScreen
import com.example.todolist.ui.feature.signup.SignUpScreen
import kotlinx.serialization.Serializable

@Serializable
object LoginRoute

@Serializable
object SignUpRoute

@Serializable
object ListRoute

@Serializable
data class AddEditRoute(val id: Long? = null)

@Composable
fun TodoNavHost() {
    val navController = rememberNavController()
    val authRepository = remember { AuthRepositoryImpl() }
    
    // Determine start destination based on auth state
    val startDestination: Any = if (authRepository.isLoggedIn) {
        ListRoute
    } else {
        LoginRoute
    }
    
    NavHost(navController = navController, startDestination = startDestination) {
        composable<LoginRoute> {
            LoginScreen(
                navigateToList = {
                    navController.navigate(ListRoute) {
                        popUpTo(LoginRoute) { inclusive = true }
                    }
                },
                navigateToSignUp = {
                    navController.navigate(SignUpRoute)
                }
            )
        }
        
        composable<SignUpRoute> {
            SignUpScreen(
                navigateToList = {
                    navController.navigate(ListRoute) {
                        popUpTo(LoginRoute) { inclusive = true }
                    }
                },
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable<ListRoute> {
            ListScreen(
                navigateToAddEditScreen = { id ->
                    navController.navigate(AddEditRoute(id = id))
                },
                navigateToLogin = {
                    navController.navigate(LoginRoute) {
                        popUpTo(ListRoute) { inclusive = true }
                    }
                }
            )
        }

        composable<AddEditRoute> { backStackEntry ->
            val addEditRoute = backStackEntry.toRoute<AddEditRoute>()
            AddEditScreen(
                id = addEditRoute.id,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}