package com.example.financialtrackerapp.presentation.navigation

import com.example.financialtrackerapp.R

sealed class BottomItem(val title: String, val iconId: Int) {
    data object TransactionsScreen : BottomItem("Transactions", R.drawable.ic_transactions)
    data object AnalysisScreen : BottomItem("Analysis", R.drawable.ic_analysis)
    data object TransactionsDialogue: BottomItem("",R.drawable.ic_create_transaction)
    data object BudgetsScreen : BottomItem("Budgets", R.drawable.ic_budgets)
    data object AdvicesScreen : BottomItem("Advices", R.drawable.ic_advices)
}