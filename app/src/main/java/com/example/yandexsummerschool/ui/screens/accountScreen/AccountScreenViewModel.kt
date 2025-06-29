package com.example.yandexsummerschool.ui.screens.accountScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel для экрана аккаунта. Хранит состояние аккаунта пользователя [AccountState].
 */
class AccountScreenViewModel : ViewModel() {
    private val mockBalance =
        AccountState.Content(
            "Mock account",
            "100 000",
            "RUB",
        )
    private val _accountState = MutableStateFlow(mockBalance)
    val accountState: StateFlow<AccountState> = _accountState
}

/**
 * Состояния экрана аккаунта: содержимое, загрузка, пусто.
 */
sealed interface AccountState {
    data class Content(
        val name: String,
        val balance: String,
        val currency: String,
    ) : AccountState

    data object Loading : AccountState

    data object Empty : AccountState
}
