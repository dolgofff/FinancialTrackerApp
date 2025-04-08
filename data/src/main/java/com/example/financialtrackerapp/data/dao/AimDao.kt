package com.example.financialtrackerapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.financialtrackerapp.data.entity.AimEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AimDao {
    @Insert
    suspend fun insertAim(aimEntity: AimEntity): Long

    @Update
    suspend fun updateAim(aimEntity: AimEntity): Int

    @Delete
    suspend fun deleteAim(aimEntity: AimEntity): Int

    @Query("DELETE FROM aims WHERE id = :aimId")
    suspend fun deleteAimById(aimId: Long): Int

    @Query("SELECT * FROM aims WHERE id = :aimId")
    suspend fun getAimById(aimId: Long): AimEntity?

    @Query("SELECT * FROM aims WHERE accountId = :accountId")
    fun getAccountsAims(accountId: Long): Flow<List<AimEntity>>
}