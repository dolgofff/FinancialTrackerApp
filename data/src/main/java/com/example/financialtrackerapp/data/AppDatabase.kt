package com.example.financialtrackerapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.financialtrackerapp.data.dao.AccountDao
import com.example.financialtrackerapp.data.dao.AdviceDao
import com.example.financialtrackerapp.data.dao.AimDao
import com.example.financialtrackerapp.data.dao.BudgetDao
import com.example.financialtrackerapp.data.dao.TransactionDao
import com.example.financialtrackerapp.data.dao.UserDao
import com.example.financialtrackerapp.data.entity.AccountEntity
import com.example.financialtrackerapp.data.entity.AccountUserCrossRef
import com.example.financialtrackerapp.data.entity.AdviceEntity
import com.example.financialtrackerapp.data.entity.AimEntity
import com.example.financialtrackerapp.data.entity.BudgetEntity
import com.example.financialtrackerapp.data.entity.TransactionEntity
import com.example.financialtrackerapp.data.entity.UserEntity

@Database(
    entities = [AccountEntity::class, AccountUserCrossRef::class, AdviceEntity::class, AimEntity::class,
        BudgetEntity::class, TransactionEntity::class, UserEntity::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao
    abstract fun adviceDao(): AdviceDao
    abstract fun aimDao(): AimDao
    abstract fun budgetDao(): BudgetDao
    abstract fun transactionDao(): TransactionDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ft_database"
                ).build()

                INSTANCE = instance

                instance
            }
        }
    }

}
