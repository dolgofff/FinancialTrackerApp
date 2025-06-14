package com.example.financialtrackerapp.app.di

import com.example.financialtrackerapp.domain.repository.AccountRepository
import com.example.financialtrackerapp.domain.repository.AdviceRepository
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
import com.example.financialtrackerapp.domain.usecase.account.GetUsersAccountsUseCase
import com.example.financialtrackerapp.domain.usecase.account.SaveCurrentAccountIdUseCase
import com.example.financialtrackerapp.domain.usecase.account.UpdateAccountUseCase
import com.example.financialtrackerapp.domain.usecase.advices.CreateAdviceUseCase
import com.example.financialtrackerapp.domain.usecase.advices.DeleteAdviceUseCase
import com.example.financialtrackerapp.domain.usecase.advices.GenerateAdvicesUseCase
import com.example.financialtrackerapp.domain.usecase.advices.GetAllAdvicesUseCase
import com.example.financialtrackerapp.domain.usecase.aims.CreateAimUseCase
import com.example.financialtrackerapp.domain.usecase.aims.GetAllAimsUseCase
import com.example.financialtrackerapp.domain.usecase.aims.UpdateAimUseCase
import com.example.financialtrackerapp.domain.usecase.budgets.CreateBudgetUseCase
import com.example.financialtrackerapp.domain.usecase.budgets.GetAllBudgetsUseCase
import com.example.financialtrackerapp.domain.usecase.security.AuthenticationUseCase
import com.example.financialtrackerapp.domain.usecase.security.ForgottenPasswordUseCase
import com.example.financialtrackerapp.domain.usecase.security.InitializeUserUseCase
import com.example.financialtrackerapp.domain.usecase.security.LogoutUseCase
import com.example.financialtrackerapp.domain.usecase.security.RegistrationUseCase
import com.example.financialtrackerapp.domain.usecase.security.SplashUseCase
import com.example.financialtrackerapp.domain.usecase.transactions.DeleteTransactionUseCase
import com.example.financialtrackerapp.domain.usecase.transactions.GetAllTransactionsUseCase
import com.example.financialtrackerapp.domain.usecase.transactions.UpdateTransactionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    @ViewModelScoped
    fun provideAuthenticationUseCase(
        userRepository: UserRepository,
        authRepository: AuthRepository,
        globalRepository: GlobalRepository
    ): AuthenticationUseCase {
        return AuthenticationUseCase(userRepository, authRepository, globalRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideForgottenPasswordUseCase(userRepository: UserRepository): ForgottenPasswordUseCase {
        return ForgottenPasswordUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideLogoutUseCase(authRepository: AuthRepository): LogoutUseCase {
        return LogoutUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideRegistrationUseCase(userRepository: UserRepository): RegistrationUseCase {
        return RegistrationUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSplashUseCase(authRepository: AuthRepository): SplashUseCase {
        return SplashUseCase(authRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideInitializeUserUseCase(globalRepository: GlobalRepository): InitializeUserUseCase {
        return InitializeUserUseCase(globalRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideCreateAccountUseCase(
        accountRepository: AccountRepository,
        crossRefRepository: CrossRefRepository,
        globalRepository: GlobalRepository,
    ): CreateAccountUseCase {
        return CreateAccountUseCase(
            accountRepository,
            crossRefRepository,
            globalRepository
        )
    }

    @Provides
    @ViewModelScoped
    fun provideDeleteAccountByIdUseCase(accountRepository: AccountRepository): DeleteAccountByIdUseCase {
        return DeleteAccountByIdUseCase(accountRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideDeleteAccountUseCase(accountRepository: AccountRepository): DeleteAccountUseCase {
        return DeleteAccountUseCase(accountRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetUsersAccountsUseCase(userRepository: UserRepository): GetUsersAccountsUseCase {
        return GetUsersAccountsUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSaveCurrentAccountIdUseCase(globalRepository: GlobalRepository): SaveCurrentAccountIdUseCase {
        return SaveCurrentAccountIdUseCase(globalRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateAccountIdUseCase(accountRepository: AccountRepository): UpdateAccountUseCase {
        return UpdateAccountUseCase(accountRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetAccountByIdUseCase(accountRepository: AccountRepository): GetAccountByIdUseCase {
        return GetAccountByIdUseCase(accountRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideDeleteTransactionUseCase(transactionRepository: TransactionRepository): DeleteTransactionUseCase {
        return DeleteTransactionUseCase(transactionRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetAllTransactionsUseCase(transactionRepository: TransactionRepository): GetAllTransactionsUseCase {
        return GetAllTransactionsUseCase(transactionRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateTransactionRepository(transactionRepository: TransactionRepository): UpdateTransactionUseCase {
        return UpdateTransactionUseCase(transactionRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetAllBudgetsUseCase(budgetRepository: BudgetRepository): GetAllBudgetsUseCase {
        return GetAllBudgetsUseCase(budgetRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideCreateBudgetUseCase(
        budgetRepository: BudgetRepository,
        globalRepository: GlobalRepository
    ): CreateBudgetUseCase {
        return CreateBudgetUseCase(budgetRepository, globalRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetAllAimsUseCase(aimRepository: AimRepository): GetAllAimsUseCase {
        return GetAllAimsUseCase(aimRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideCreateAimUseCase(
        aimRepository: AimRepository,
        globalRepository: GlobalRepository
    ): CreateAimUseCase {
        return CreateAimUseCase(aimRepository, globalRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateAimUseCase(aimRepository: AimRepository): UpdateAimUseCase {
        return UpdateAimUseCase(aimRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetAllAdvicesUseCase(adviceRepository: AdviceRepository): GetAllAdvicesUseCase {
        return GetAllAdvicesUseCase(adviceRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideDeleteAdviceUseCase(adviceRepository: AdviceRepository): DeleteAdviceUseCase {
        return DeleteAdviceUseCase(adviceRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideCreateAdviceUseCase(adviceRepository: AdviceRepository): CreateAdviceUseCase {
        return CreateAdviceUseCase(adviceRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGenerateAdvicesUseCase(): GenerateAdvicesUseCase {
        return GenerateAdvicesUseCase()
    }
}
