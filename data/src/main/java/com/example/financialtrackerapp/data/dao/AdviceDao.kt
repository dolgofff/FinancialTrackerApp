package com.example.financialtrackerapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.financialtrackerapp.data.entity.AdviceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AdviceDao {
    @Insert
    suspend fun insertAdvice(adviceEntity: AdviceEntity): Long

    @Update
    suspend fun updateAdvice(adviceEntity: AdviceEntity): Int

    @Delete
    suspend fun deleteAdvice(adviceEntity: AdviceEntity): Int

    @Query("DELETE FROM advices WHERE id = :adviceId")
    suspend fun deleteAdviceById(adviceId: Long): Int

    @Query("SELECT * FROM advices WHERE id = :adviceId")
    suspend fun getAdviceById(adviceId: Long): AdviceEntity?

    @Query("SELECT * FROM advices WHERE type = :adviceType")
    fun getAdvicesByType(adviceType: String): Flow<List<AdviceEntity>>

    @Query("SELECT * FROM advices WHERE accountId = :accountId")
    fun getAccountsAdvices(accountId: Long): Flow<List<AdviceEntity>>

}