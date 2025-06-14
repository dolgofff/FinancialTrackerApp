package com.example.financialtrackerapp.app.di

import com.example.financialtrackerapp.domain.repository.AccountRepository
import com.example.financialtrackerapp.domain.repository.BudgetRepository
import com.example.financialtrackerapp.domain.repository.GlobalRepository
import com.example.financialtrackerapp.domain.repository.TransactionRepository
import com.example.financialtrackerapp.domain.usecase.account.GetCurrentAccountUseCase
import com.example.financialtrackerapp.domain.usecase.budgets.UpdateSpentValueUseCase
import com.example.financialtrackerapp.domain.usecase.transactions.CreateTransactionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {
    @Provides
    @Singleton
    fun provideGetCurrentAccountUseCase(
        globalRepository: GlobalRepository,
        accountRepository: AccountRepository
    ): GetCurrentAccountUseCase {
        return GetCurrentAccountUseCase(globalRepository, accountRepository)
    }

    @Provides
    @Singleton
    fun provideCreateTransactionUseCase(transactionRepository: TransactionRepository): CreateTransactionUseCase {
        return CreateTransactionUseCase(transactionRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateSpentValueUseCase(budgetRepository: BudgetRepository): UpdateSpentValueUseCase {
        return UpdateSpentValueUseCase(budgetRepository)
    }
}