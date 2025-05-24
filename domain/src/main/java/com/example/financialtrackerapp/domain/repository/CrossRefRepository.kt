package com.example.financialtrackerapp.domain.repository

import com.example.financialtrackerapp.domain.model.CrossRef

interface CrossRefRepository {
    suspend fun insert(crossRef: CrossRef): Boolean
}