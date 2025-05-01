package com.example.financialtrackerapp.domain.usecase.account

import com.example.financialtrackerapp.domain.model.Account
import com.example.financialtrackerapp.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class GetUsersAccountsUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(username: String): Flow<List<Account>> {
        return withContext(Dispatchers.IO) {
            val user = userRepository.getByUsername(username)
            user?.let {
                userRepository.getAllUsersAccounts(user.id)
            } ?: flowOf(emptyList())
        }
    }
}