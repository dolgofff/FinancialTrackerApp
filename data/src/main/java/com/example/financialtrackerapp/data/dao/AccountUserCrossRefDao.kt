package com.example.financialtrackerapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.financialtrackerapp.data.entity.AccountUserCrossRef

@Dao
interface AccountUserCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(accountUser: AccountUserCrossRef): Long
}