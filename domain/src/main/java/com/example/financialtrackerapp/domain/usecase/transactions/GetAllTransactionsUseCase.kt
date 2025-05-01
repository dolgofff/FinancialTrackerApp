package com.example.financialtrackerapp.domain.usecase.transactions

import com.example.financialtrackerapp.domain.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAllTransactionsUseCase(private val transactionRepository: TransactionRepository) {
    suspend operator fun invoke(accountId: Long) = withContext(Dispatchers.IO) {
        transactionRepository.getAll(accountId)
    }
}