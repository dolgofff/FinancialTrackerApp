package com.example.financialtrackerapp.domain.usecase.transactions

import com.example.financialtrackerapp.domain.model.Transaction
import com.example.financialtrackerapp.domain.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteTransactionUseCase(private val transactionRepository: TransactionRepository) {
    suspend operator fun invoke(transaction: Transaction) = withContext(Dispatchers.IO){
        transactionRepository.delete(transaction)
    }
}