package com.example.yandexsummerschool.data.local

interface UserCurrencyPrefs {
    suspend fun getCurrency(): String?

    suspend fun saveCurrency(currency: String)
}
