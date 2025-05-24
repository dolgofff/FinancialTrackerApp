package com.example.financialtrackerapp.presentation.screen.advices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financialtrackerapp.domain.model.Transaction
import com.example.financialtrackerapp.domain.usecase.account.GetCurrentAccountUseCase
import com.example.financialtrackerapp.domain.usecase.transactions.GetAllTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.YearMonth
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class AdvicesViewModel @Inject constructor(
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase,
    private val getCurrentAccountUseCase: GetCurrentAccountUseCase,
) : ViewModel() {

    private val _advices = MutableStateFlow<List<Advice>>(emptyList())
    val advices = _advices.asStateFlow()

    init {
        loadAdvices()
    }

    private fun loadAdvices() {
        viewModelScope.launch {
            val currentAccount = getCurrentAccountUseCase()
            currentAccount?.let {
                getAllTransactionsUseCase(it.id).collect { transactions ->
                    _advices.value = generateAdvices(transactions)
                }
            }
        }
    }

    private fun generateAdvices(transactions: List<Transaction>): List<Advice> {
        val adviceList = mutableListOf<Advice>()

        val groupedByMonth = transactions.groupBy {
            YearMonth.from(
                Instant.ofEpochMilli(it.date).atZone(ZoneId.systemDefault()).toLocalDate()
            )
        }

        val last3Months = groupedByMonth
            .toSortedMap(compareByDescending { it })
            .values
            .take(3)

        val byCategory = last3Months.flatten().groupBy { it.category }

        val categoryAverages = byCategory.mapValues { (_, list) ->
            list.sumOf { it.amount } / last3Months.size
        }

        val latestMonth = last3Months.firstOrNull() ?: emptyList()
        val latestByCategory = latestMonth.groupBy { it.category }

        for ((category, transactionInLatestMonth) in latestByCategory) {
            val totalThisMonth = transactionInLatestMonth.sumOf { it.amount }
            val average = categoryAverages[category] ?: continue

            if (totalThisMonth > average * 1.5) {
                adviceList.add(
                    Advice(
                        message = "Your spending on $category has increased by more than 50% compared to the average. Consider reviewing this category!",
                        type = AdviceType.CRITICAL
                    )
                )
            } else if (totalThisMonth > average * 1.2) {
                adviceList.add(
                    Advice(
                        message = "Your spending on $category is slightly above the usual. Keep an eye on it.",
                        type = AdviceType.WARNING
                    )
                )
            } else if (totalThisMonth < average * 0.8) {
                adviceList.add(
                    Advice(
                        message = "Good job! You have reduced your spending on $category this month.",
                        type = AdviceType.POSITIVE
                    )
                )
            }
        }

        val income = latestMonth
            .filter { it.type.name == "INCOME" }
            .sumOf { it.amount }

        val expense = latestMonth
            .filter { it.type.name == "EXPENSE" || it.type.name == "TRANSFER" }
            .sumOf { it.amount }

        if (expense > income) {
            adviceList.add(
                Advice(
                    message = "Your expenses this month exceed your income. Consider adjusting your budget.",
                    type = AdviceType.CRITICAL
                )
            )
        } else if ((income - expense) / income > 0.3) {
            adviceList.add(
                Advice(
                    message = "Great work! You saved more than 30% of your income this month!",
                    type = AdviceType.POSITIVE
                )
            )
        }

        return adviceList
    }

    data class Advice(
        val message: String,
        val type: AdviceType,
    )

    enum class AdviceType {
        CRITICAL,
        WARNING,
        POSITIVE,
    }
}

