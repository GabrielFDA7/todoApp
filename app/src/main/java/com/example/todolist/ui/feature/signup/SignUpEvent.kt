package com.example.todolist.ui.feature.signup

sealed interface SignUpEvent {
    data class EmailChanged(val email: String) : SignUpEvent
    data class PasswordChanged(val password: String) : SignUpEvent
    data class ConfirmPasswordChanged(val confirmPassword: String) : SignUpEvent
    data object Submit : SignUpEvent
    data object NavigateToLogin : SignUpEvent
}
