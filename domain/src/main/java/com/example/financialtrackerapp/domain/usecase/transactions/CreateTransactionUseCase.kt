package com.example.financialtrackerapp.domain.usecase.transactions

import com.example.financialtrackerapp.domain.model.Transaction
import com.example.financialtrackerapp.domain.model.enums.Category
import com.example.financialtrackerapp.domain.model.enums.TransactionType
import com.example.financialtrackerapp.domain.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateTransactionUseCase(private val transactionRepository: TransactionRepository) {
    suspend operator fun invoke(
        accountId: Long,
        amount: Double,
        category: Category,
        type: TransactionType,
        date: Long,
        note: String?,
        place: String?
    ) = withContext(Dispatchers.IO) {
        transactionRepository.insert(
            Transaction(
                id = 0,
                accountId = accountId,
                amount = amount,
                category = category,
                type = type,
                date = date,
                note = note,
                place = place
            )
        )
    }
}