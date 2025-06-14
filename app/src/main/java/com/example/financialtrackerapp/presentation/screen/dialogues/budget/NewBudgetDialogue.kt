package com.example.financialtrackerapp.presentation.screen.dialogues.budget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.financialtrackerapp.presentation.ui.components.CategoryDropdown
import com.example.financialtrackerapp.presentation.ui.components.DateInputField
import com.example.financialtrackerapp.presentation.ui.components.SpecialAmountInputField
import com.example.financialtrackerapp.presentation.ui.components.SubmissionButton
import com.example.financialtrackerapp.presentation.ui.components.parseFormattedNumber
import com.example.financialtrackerapp.presentation.ui.theme.BottomBarColor
import com.example.financialtrackerapp.presentation.ui.theme.White
import com.example.financialtrackerapp.presentation.ui.theme.poppinsFontFamily

@Composable
fun BudgetDialog(
    onDismiss: () -> Unit,
    budgetViewModel: NewBudgetViewModel = hiltViewModel()
) {
    val budgetState by budgetViewModel.budgetState.collectAsState()

    Dialog(
        onDismissRequest = {
            budgetViewModel.clear()
            onDismiss()
        }) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
                .clip(RoundedCornerShape(46.dp))
                .background(BottomBarColor),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 12.dp),
                text = "Новый бюджет",
                fontSize = 24.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = White,
            )

            Column(
                modifier = Modifier.align(Alignment.Center),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SpecialAmountInputField(
                    label = "Лимит",
                    amount = budgetState.limitAmount,
                    showDivisor = false,
                    onAmountChange = {
                        budgetViewModel.updateLimitAmount(
                            parseFormattedNumber(it)
                        )
                    }
                )

                SpecialAmountInputField(
                    label = "Потрачено",
                    amount = budgetState.spentAmount,
                    showDivisor = true,
                    onAmountChange = {
                        budgetViewModel.updateSpentAmount(
                            parseFormattedNumber(it)
                        )
                    }
                )

                CategoryDropdown(
                    selectedCategory = budgetState.category,
                    onCategoryChange = budgetViewModel::updateCategory
                )

                DateInputField(
                    label = "Начало",
                    date = budgetState.startDate,
                    onDateSelected = budgetViewModel::updateStartDate
                )

                DateInputField(
                    label = "Конец",
                    date = budgetState.endDate,
                    onDateSelected = budgetViewModel::updateEndDate
                )

                Spacer(modifier = Modifier.size(72.dp))

                SubmissionButton(
                    title = "Создать бюджет",
                    onClick = {
                        budgetViewModel.createBudget()
                    }
                )
            }
        }
    }

    LaunchedEffect(budgetState.isCreated) {
        if (budgetState.isCreated) {
            budgetViewModel.clear()
            onDismiss()
        }
    }
}