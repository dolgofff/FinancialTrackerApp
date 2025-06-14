package com.example.financialtrackerapp.presentation.screen.advices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financialtrackerapp.domain.model.Advice
import com.example.financialtrackerapp.domain.usecase.account.GetCurrentAccountUseCase
import com.example.financialtrackerapp.domain.usecase.advices.CreateAdviceUseCase
import com.example.financialtrackerapp.domain.usecase.advices.DeleteAdviceUseCase
import com.example.financialtrackerapp.domain.usecase.advices.GenerateAdvicesUseCase
import com.example.financialtrackerapp.domain.usecase.advices.GetAllAdvicesUseCase
import com.example.financialtrackerapp.domain.usecase.transactions.GetAllTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdvicesViewModel @Inject constructor(
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase,
    private val getCurrentAccountUseCase: GetCurrentAccountUseCase,
    private val getAllAdvicesUseCase: GetAllAdvicesUseCase,
    private val deleteAdviceUseCase: DeleteAdviceUseCase,
    private val createAdviceUseCase: CreateAdviceUseCase,
    private val generateAdvicesUseCase: GenerateAdvicesUseCase,
) : ViewModel() {
    private val _advices = MutableStateFlow<List<Advice>>(emptyList())
    val advices = _advices.asStateFlow()

    init {
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch {
            val currentAccount = getCurrentAccountUseCase()
            currentAccount?.let { account ->

                getAllTransactionsUseCase(account.id).collect { transactions ->

                    val oldAdvices = getAllAdvicesUseCase(account.id).first()

                    val newAdvices =
                        generateAdvicesUseCase(accountId = account.id, transactions = transactions)
                            .map { it.copy(accountId = account.id) }

                    val oldComparable = oldAdvices.map { it.type to it.content }.toSet()
                    val newComparable = newAdvices.map { it.type to it.content }.toSet()

                    if (oldComparable != newComparable) {
                        oldAdvices.forEach { deleteAdviceUseCase(it) }
                        newAdvices.forEach {
                            createAdviceUseCase(
                                accountId = it.accountId,
                                content = it.content,
                                type = it.type
                            )
                        }
                        _advices.value = newAdvices
                    } else {
                        _advices.value = oldAdvices
                    }
                }
            }
        }
    }
}


