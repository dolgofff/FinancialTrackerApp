package com.example.financialtrackerapp.app.di

import com.example.financialtrackerapp.domain.repository.AccountRepository
import com.example.financialtrackerapp.domain.repository.AimRepository
import com.example.financialtrackerapp.domain.repository.AuthRepository
import com.example.financialtrackerapp.domain.repository.BudgetRepository
import com.example.financialtrackerapp.domain.repository.CrossRefRepository
import com.example.financialtrackerapp.domain.repository.GlobalRepository
import com.example.financialtrackerapp.domain.repository.TransactionRepository
import com.example.financialtrackerapp.domain.repository.UserRepository
import com.example.financialtrackerapp.domain.usecase.account.CreateAccountUseCase
import com.example.financialtrackerapp.domain.usecase.account.DeleteAccountByIdUseCase
import com.example.financialtrackerapp.domain.usecase.account.DeleteAccountUseCase
import com.example.financialtrackerapp.domain.usecase.account.GetAccountByIdUseCase
import com.example.financialtrackerapp.domain.usecase.account.GetCurrentAccountUseCase
import com.example.financialtrackerapp.domain.usecase.account.GetUsersAccountsUseCase
import com.example.financialtrackerapp.domain.usecase.account.SaveCurrentAccountIdUseCase
import com.example.financialtrackerapp.domain.usecase.account.UpdateAccountUseCase
import com.example.financialtrackerapp.domain.usecase.aims.GetAllAimsUseCase
import com.example.financialtrackerapp.domain.usecase.budgets.GetAllBudgetsUseCase
import com.example.financialtrackerapp.domain.usecase.security.AuthenticationUseCase
import com.example.financialtrackerapp.domain.usecase.security.ForgottenPasswordUseCase
import com.example.financialtrackerapp.domain.usecase.security.InitializeUserUseCase
import com.example.financialtrackerapp.domain.usecase.security.LogoutUseCase
import com.example.financialtrackerapp.domain.usecase.security.RegistrationUseCase
import com.example.financialtrackerapp.domain.usecase.security.SplashUseCase
import com.example.financialtrackerapp.domain.usecase.transactions.CreateTransactionUseCase
import com.example.financialtrackerapp.domain.usecase.transactions.DeleteTransactionUseCase
import com.example.financialtrackerapp.domain.usecase.transactions.GetAllTransactionsUseCase
import com.example.financialtrackerapp.domain.usecase.transactions.UpdateTransactionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideAuthenticationUseCase(
        userRepository: UserRepository,
        authRepository: AuthRepository,
        globalRepository: GlobalRepository
    ): AuthenticationUseCase {
        return AuthenticationUseCase(userRepository, authRepository, globalRepository)
    }

    @Provides
    fun provideForgottenPasswordUseCase(userRepository: UserRepository): ForgottenPasswordUseCase {
        return ForgottenPasswordUseCase(userRepository)
    }

    @Provides
    fun provideLogoutUseCase(authRepository: AuthRepository): LogoutUseCase {
        return LogoutUseCase(authRepository)
    }

    @Provides
    fun provideRegistrationUseCase(userRepository: UserRepository): RegistrationUseCase {
        return RegistrationUseCase(userRepository)
    }

    @Provides
    fun provideSplashUseCase(authRepository: AuthRepository): SplashUseCase {
        return SplashUseCase(authRepository)
    }

    @Provides
    fun provideInitializeUserUseCase(globalRepository: GlobalRepository): InitializeUserUseCase {
        return InitializeUserUseCase(globalRepository)
    }

    @Provides
    fun provideCreateAccountUseCase(
        accountRepository: AccountRepository,
        crossRefRepository: CrossRefRepository,
        globalRepository: GlobalRepository,
        userRepository: UserRepository,
    ): CreateAccountUseCase {
        return CreateAccountUseCase(
            accountRepository,
            crossRefRepository,
            globalRepository,
            userRepository
        )
    }

    @Provides
    fun provideDeleteAccountByIdUseCase(accountRepository: AccountRepository): DeleteAccountByIdUseCase {
        return DeleteAccountByIdUseCase(accountRepository)
    }

    @Provides
    fun provideDeleteAccountUseCase(accountRepository: AccountRepository): DeleteAccountUseCase {
        return DeleteAccountUseCase(accountRepository)
    }

    @Provides
    fun provideGetCurrentAccountUseCase(
        globalRepository: GlobalRepository,
        accountRepository: AccountRepository
    ): GetCurrentAccountUseCase {
        return GetCurrentAccountUseCase(globalRepository, accountRepository)
    }

    @Provides
    fun provideGetUsersAccountsUseCase(userRepository: UserRepository): GetUsersAccountsUseCase {
        return GetUsersAccountsUseCase(userRepository)
    }

    @Provides
    fun provideSaveCurrentAccountIdUseCase(globalRepository: GlobalRepository): SaveCurrentAccountIdUseCase {
        return SaveCurrentAccountIdUseCase(globalRepository)
    }

    @Provides
    fun provideUpdateAccountIdUseCase(accountRepository: AccountRepository): UpdateAccountUseCase {
        return UpdateAccountUseCase(accountRepository)
    }

    @Provides
    fun provideGetAccountByIdUseCase(accountRepository: AccountRepository): GetAccountByIdUseCase {
        return GetAccountByIdUseCase(accountRepository)
    }

    @Provides
    fun provideCreateTransactionUseCase(transactionRepository: TransactionRepository): CreateTransactionUseCase {
        return CreateTransactionUseCase(transactionRepository)
    }

    @Provides
    fun provideDeleteTransactionUseCase(transactionRepository: TransactionRepository): DeleteTransactionUseCase {
        return DeleteTransactionUseCase(transactionRepository)
    }

    @Provides
    fun provideGetAllTransactionsUseCase(transactionRepository: TransactionRepository): GetAllTransactionsUseCase {
        return GetAllTransactionsUseCase(transactionRepository)
    }

    @Provides
    fun provideUpdateTransactionRepository(transactionRepository: TransactionRepository): UpdateTransactionUseCase {
        return UpdateTransactionUseCase(transactionRepository)
    }

    @Provides
    fun provideGetAllBudgetsUseCase(budgetRepository: BudgetRepository): GetAllBudgetsUseCase {
        return GetAllBudgetsUseCase(budgetRepository)
    }

    @Provides
    fun provideGetAllAimsUseCase(aimRepository: AimRepository): GetAllAimsUseCase {
        return GetAllAimsUseCase(aimRepository)
    }


}
