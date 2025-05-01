package com.example.financialtrackerapp.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.IOException
import androidx.datastore.dataStore
import com.example.financialtrackerapp.data.entity.temporary.TokenData
import com.example.financialtrackerapp.data.entity.temporary.TokenSerializer
import com.example.financialtrackerapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.protoDataStore by dataStore("token_storage.json", TokenSerializer)

class AuthRepositoryImpl(private val context: Context) : AuthRepository {

    override suspend fun saveToken(token: String) {
        context.protoDataStore.updateData {
            TokenData(token = token, lastActive = System.currentTimeMillis())
        }
    }

    override suspend fun getToken(): String {
        return try {
            context.protoDataStore.data.map { it.token }.first()
        } catch (e: IOException) {
            Log.e("AuthRepository:", "Error while reading token (getToken)", e)
            "none"
        }
    }

    override suspend fun getLastActive(): Long {
        return try {
            context.protoDataStore.data.map { it.lastActive }.first()
        } catch (e: IOException) {
            0L
        }
    }

    override suspend fun isLoggedIn(): Boolean {
        val token = getToken()
        return token != "none" && token != "reading_failure"
    }

    override suspend fun updateLastActive() {
        context.protoDataStore.updateData { current ->
            current.copy(lastActive = System.currentTimeMillis())
        }
    }

    override suspend fun shouldLogout(): Boolean {
        val shouldLogout =
            (System.currentTimeMillis() - getLastActive()) > (7 * 24 * 60 * 60 * 1000L)
        if (shouldLogout) logout()
        return shouldLogout
    }

    override suspend fun logout() {
        context.protoDataStore.updateData {
            TokenData(token = "none", 0L)
        }
    }
}