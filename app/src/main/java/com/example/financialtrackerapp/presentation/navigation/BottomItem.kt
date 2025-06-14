package com.example.financialtrackerapp.presentation.navigation

import com.example.financialtrackerapp.R

sealed class BottomItem(val title: String, val iconId: Int) {
    data object TransactionsScreen : BottomItem("Операции", R.drawable.ic_transactions)
    data object AnalysisScreen : BottomItem("Статистика", R.drawable.ic_analysis)
    data object TransactionsDialogue: BottomItem("",R.drawable.ic_create_transaction)
    data object BudgetsScreen : BottomItem("Бюджеты", R.drawable.ic_budgets)
    data object AdvicesScreen : BottomItem("Советы", R.drawable.ic_advices)
}