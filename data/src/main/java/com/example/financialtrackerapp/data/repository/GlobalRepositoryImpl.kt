package com.example.financialtrackerapp.data.repository

import android.content.Context
import androidx.datastore.core.IOException
import androidx.datastore.dataStore
import com.example.financialtrackerapp.data.entity.temporary.GlobalData
import com.example.financialtrackerapp.data.entity.temporary.GlobalSerializer
import com.example.financialtrackerapp.domain.repository.GlobalRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.protoDataStore by dataStore("global_data_storage.json", GlobalSerializer)

class GlobalRepositoryImpl(private val context: Context) : GlobalRepository {

    override suspend fun saveUsername(username: String) {
        context.protoDataStore.updateData {
            GlobalData(username = username)
        }
    }

    override suspend fun saveCurrentAccountId(currentAccountId: Long) {
        context.protoDataStore.updateData {
            it.copy(currentAccountId = currentAccountId)
        }
    }

    override suspend fun getUsername(): String {
        return try {
            context.protoDataStore.data.map { it.username }.first()
        } catch (e: IOException) {
            "noneU"
        }
    }

    override suspend fun getCurrentAccountId(): Long {
        return try {
            context.protoDataStore.data.map { it.currentAccountId }.first()
        } catch (e: IOException) {
            0L
        }
    }
}