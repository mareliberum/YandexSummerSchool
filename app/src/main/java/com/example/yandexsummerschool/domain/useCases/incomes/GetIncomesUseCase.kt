package com.example.yandexsummerschool.domain.useCases.incomes

import com.example.yandexsummerschool.data.dto.Result
import com.example.yandexsummerschool.domain.models.TransactionModel
import com.example.yandexsummerschool.domain.repositories.TransactionsRepository
import javax.inject.Inject

/**
 * Возвращает список доходных транзакций
 * @param repository [TransactionsRepository]
 */
class GetIncomesUseCase
    @Inject
    constructor(
        private val repository: TransactionsRepository,
    ) {
        suspend operator fun invoke(
            accountId: Int,
            startDate: String? = null,
            endDate: String? = null,
        ): Result<List<TransactionModel>> {
            return try {
                repository.getIncomeTransactions(accountId, startDate, endDate)
            } catch (e: Exception) {
                Result.Failure(e)
            }
        }
    }
