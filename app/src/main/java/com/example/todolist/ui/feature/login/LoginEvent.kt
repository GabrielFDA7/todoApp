package com.example.todolist.ui.feature.login

sealed interface LoginEvent {
    data class EmailChanged(val email: String) : LoginEvent
    data class PasswordChanged(val password: String) : LoginEvent
    data object Submit : LoginEvent
    data object NavigateToSignUp : LoginEvent
}
