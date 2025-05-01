package com.example.financialtrackerapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index

//Таблица связывает пользователей с их счетами (используется для shared-аккаунтов), связь М:М
@Entity(
    tableName = "account_users",
    primaryKeys = ["accountId", "userId"],
    foreignKeys = [
        ForeignKey(
            entity = AccountEntity::class,
            parentColumns = ["id"],        // Используется "id" из AccountEntity
            childColumns = ["accountId"],  // Ссылаемся на "accountId" в AccountUserCrossRef
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],        // Используется "id" из UserEntity
            childColumns = ["userId"],     // Ссылаемся на "userId" в AccountUserCrossRef
            onDelete = CASCADE
        )
    ],
    indices = [
        Index("accountId", unique = false),
        Index("userId", unique = false),
        Index(value = ["accountId", "userId"], unique = true)
    ]
)
data class AccountUserCrossRef(
    @ColumnInfo(name = "accountId") val accountId: Long,
    @ColumnInfo(name = "userId") val userId: Long
)