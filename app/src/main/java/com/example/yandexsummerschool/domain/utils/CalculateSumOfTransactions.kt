package com.example.yandexsummerschool.domain.utils

import com.example.yandexsummerschool.domain.models.TransactionDomainModel

fun calculateSum(transactions: List<TransactionDomainModel>): String {
    val total =
        transactions.sumOf {
            it.amount.toDouble()
        }
    return "$total"
}
