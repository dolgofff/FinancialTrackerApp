package com.example.financialtrackerapp.app.di

import android.content.Context
import com.example.financialtrackerapp.data.AppDatabase
import com.example.financialtrackerapp.data.dao.AccountDao
import com.example.financialtrackerapp.data.dao.AccountUserCrossRefDao
import com.example.financialtrackerapp.data.dao.AdviceDao
import com.example.financialtrackerapp.data.dao.AimDao
import com.example.financialtrackerapp.data.dao.BudgetDao
import com.example.financialtrackerapp.data.dao.TransactionDao
import com.example.financialtrackerapp.data.dao.UserDao
import com.example.financialtrackerapp.data.repository.AccountRepositoryImpl
import com.example.financialtrackerapp.data.repository.AdviceRepositoryImpl
import com.example.financialtrackerapp.data.repository.AimRepositoryImpl
import com.example.financialtrackerapp.data.repository.AuthRepositoryImpl
import com.example.financialtrackerapp.data.repository.BudgetRepositoryImpl
import com.example.financialtrackerapp.data.repository.CrossRefRepositoryImpl
import com.example.financialtrackerapp.data.repository.GlobalRepositoryImpl
import com.example.financialtrackerapp.data.repository.TransactionRepositoryImpl
import com.example.financialtrackerapp.data.repository.UserRepositoryImpl
import com.example.financialtrackerapp.domain.repository.AccountRepository
import com.example.financialtrackerapp.domain.repository.AdviceRepository
import com.example.financialtrackerapp.domain.repository.AimRepository
import com.example.financialtrackerapp.domain.repository.AuthRepository
import com.example.financialtrackerapp.domain.repository.BudgetRepository
import com.example.financialtrackerapp.domain.repository.CrossRefRepository
import com.example.financialtrackerapp.domain.repository.GlobalRepository
import com.example.financialtrackerapp.domain.repository.TransactionRepository
import com.example.financialtrackerapp.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(@ApplicationContext context: Context): AuthRepository {
        return AuthRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideGlobalRepository(@ApplicationContext context: Context): GlobalRepository {
        return GlobalRepositoryImpl(context)
    }

    @Provides
    fun provideAccountDao(database: AppDatabase): AccountDao {
        return database.accountDao()
    }

    @Provides
    fun provideAccountUserCrossRefDao(database: AppDatabase): AccountUserCrossRefDao {
        return database.accountUserCrossRefDao()
    }

    @Provides
    fun provideAdviceDao(database: AppDatabase): AdviceDao {
        return database.adviceDao()
    }

    @Provides
    fun provideAimDao(database: AppDatabase): AimDao {
        return database.aimDao()
    }

    @Provides
    fun provideBudgetDao(database: AppDatabase): BudgetDao {
        return database.budgetDao()
    }

    @Provides
    fun provideTransactionDao(database: AppDatabase): TransactionDao {
        return database.transactionDao()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideAccountRepository(accountDao: AccountDao): AccountRepository {
        return AccountRepositoryImpl(accountDao)
    }

    @Provides
    fun provideAdviceRepository(adviceDao: AdviceDao): AdviceRepository {
        return AdviceRepositoryImpl(adviceDao)
    }

    @Provides
    fun provideAimRepository(aimDao: AimDao): AimRepository {
        return AimRepositoryImpl(aimDao)
    }

    @Provides
    fun provideBudgetRepository(budgetDao: BudgetDao): BudgetRepository {
        return BudgetRepositoryImpl(budgetDao)
    }

    @Provides
    fun provideTransactionRepository(transactionDao: TransactionDao): TransactionRepository {
        return TransactionRepositoryImpl(transactionDao)
    }

    @Provides
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepositoryImpl(userDao)
    }

    @Provides
    fun provideCrossRefRepository(accountUserCrossRefDao: AccountUserCrossRefDao): CrossRefRepository {
        return CrossRefRepositoryImpl(accountUserCrossRefDao)
    }

}