package com.example.financialtrackerapp.domain.usecase.advices

import com.example.financialtrackerapp.domain.model.Advice
import com.example.financialtrackerapp.domain.model.Transaction
import com.example.financialtrackerapp.domain.model.enums.AdviceType
import com.example.financialtrackerapp.domain.model.enums.Category
import java.time.Instant
import java.time.YearMonth
import java.time.ZoneId

class GenerateAdvicesUseCase {
    operator fun invoke(accountId: Long, transactions: List<Transaction>): List<Advice>{
        val adviceList = mutableListOf<Advice>()

        val groupedByMonth = transactions.groupBy {
            YearMonth.from(
                Instant.ofEpochMilli(it.date).atZone(ZoneId.systemDefault()).toLocalDate()
            )
        }
        val last3Months = groupedByMonth.toSortedMap(compareByDescending { it }).values.take(3)
        val byCategory = last3Months.flatten().groupBy { it.category }
        val categoryAverages =
            byCategory.mapValues { (_, list) -> list.sumOf { it.amount } / last3Months.size }
        val latestMonth = last3Months.firstOrNull() ?: emptyList()
        val latestByCategory = latestMonth.groupBy { it.category }

        adviceList += analyzeCategorySpending(accountId, latestByCategory, categoryAverages)
        adviceList += analyzeIncomeExpense(accountId, latestMonth)
        adviceList += analyzeTransactionPatterns(accountId, latestMonth)

        return adviceList
    }

    private fun analyzeCategorySpending(
        accountId: Long,
        latestByCategory: Map<Category, List<Transaction>>,
        categoryAverages: Map<Category, Double>
    ): List<Advice> {
        val advices = mutableListOf<Advice>()

        for ((category, transactions) in latestByCategory) {
            val totalThisMonth = transactions.sumOf { it.amount }
            val average = categoryAverages[category] ?: continue

            when {
                totalThisMonth > average * 1.5 -> advices += Advice(
                    accountId = accountId,
                    content = "Your spending on $category has increased by more than 50% compared to the average. Consider reviewing this category!",
                    type = AdviceType.CRITICAL
                )

                totalThisMonth > average * 1.2 -> advices += Advice(
                    accountId = accountId,
                    content = "Your spending on $category is slightly above the usual. Keep an eye on it.",
                    type = AdviceType.WARNING
                )

                totalThisMonth < average * 0.5 -> advices += Advice(
                    accountId = accountId,
                    content = "You significantly reduced your spending on $category. Nice job!",
                    type = AdviceType.POSITIVE
                )
            }

            val smallTransactionsCount = transactions.count { it.amount < average * 0.1 }
            if (smallTransactionsCount > 5) {
                advices += Advice(
                    accountId = accountId,
                    content = "You have many small transactions in $category. Consider if some of them are unnecessary.",
                    type = AdviceType.WARNING
                )
            }

            val maxTransaction = transactions.maxOfOrNull { it.amount } ?: 0.0
            if (maxTransaction > average * 2) {
                advices += Advice(
                    accountId = accountId,
                    content = "You made a large purchase of $maxTransaction in $category. Make sure it fits your budget.",
                    type = AdviceType.WARNING
                )
            }
        }
        return advices
    }

    private fun analyzeIncomeExpense(
        accountId: Long,
        latestMonth: List<Transaction>
    ): List<Advice> {
        val advices = mutableListOf<Advice>()

        val income = latestMonth.filter { it.type.name == "INCOME" }.sumOf { it.amount }
        val expense = latestMonth.filter { it.type.name == "EXPENSE" || it.type.name == "TRANSFER" }
            .sumOf { it.amount }

        if (expense > income) {
            advices += Advice(
                accountId = accountId,
                content = "Your expenses this month exceed your income. Consider adjusting your budget.",
                type = AdviceType.CRITICAL
            )
        } else if (income > 0 && (income - expense) / income > 0.3) {
            advices += Advice(
                accountId = accountId,
                content = "Great work! You saved more than 30% of your income this month!",
                type = AdviceType.POSITIVE
            )
        }

        val entertainmentExpense =
            latestMonth.filter { it.category == Category.ENTERTAINMENTS }.sumOf { it.amount }
        if (income > 0 && entertainmentExpense / income > 0.4) {
            advices += Advice(
                accountId = accountId,
                content = "You spent more than 40% of your income on entertainment. Consider balancing your budget.",
                type = AdviceType.WARNING
            )
        }

        return advices
    }

    private fun analyzeTransactionPatterns(
        accountId: Long,
        latestMonth: List<Transaction>
    ): List<Advice> {
        val advices = mutableListOf<Advice>()
        val savingTransfers =
            latestMonth.filter { it.category == Category.INVESTMENTS_INCOME && it.type.name == "INCOME" }
        if (savingTransfers.size >= 2) {
            advices += Advice(
                accountId = accountId,
                content = "You have regular investments income. Keep up the good financial discipline!",
                type = AdviceType.POSITIVE
            )
        }
        return advices
    }
}