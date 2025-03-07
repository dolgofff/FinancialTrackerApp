package com.example.financialtrackerapp.data.entity

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
            parentColumns = ["id"],
            childColumns = ["accountId"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = CASCADE
        )
    ],
    indices = [Index("accountId"), Index("userId")]
)
data class AccountUserCrossRef(
    val accountId: Long,
    val userId: Long
)