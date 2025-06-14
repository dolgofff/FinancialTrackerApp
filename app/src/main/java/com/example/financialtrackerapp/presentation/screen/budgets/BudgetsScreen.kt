package com.example.financialtrackerapp.presentation.screen.budgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.financialtrackerapp.R
import com.example.financialtrackerapp.domain.model.enums.Currency
import com.example.financialtrackerapp.presentation.screen.dialogues.aim.AimDialog
import com.example.financialtrackerapp.presentation.screen.dialogues.budget.BudgetDialog
import com.example.financialtrackerapp.presentation.screen.main.GlobalViewModel
import com.example.financialtrackerapp.presentation.ui.components.AddButton
import com.example.financialtrackerapp.presentation.ui.components.AimsBox
import com.example.financialtrackerapp.presentation.ui.components.BudgetsList
import com.example.financialtrackerapp.presentation.ui.components.FilterButton
import com.example.financialtrackerapp.presentation.ui.theme.MainBackground
import com.example.financialtrackerapp.presentation.ui.theme.SecondaryBackground
import com.example.financialtrackerapp.presentation.ui.theme.White
import com.example.financialtrackerapp.presentation.ui.theme.poppinsFontFamily

@Composable
fun BudgetsScreen(
    budgetsViewModel: BudgetsViewModel = hiltViewModel(),
    globalViewModel: GlobalViewModel
) {
    val budgetsState by budgetsViewModel.budgetsState.collectAsState()
    val globalState by globalViewModel.globalState.collectAsState()

    var showAimDialog by remember { mutableStateOf(false) }
    var showBudgetDialog by remember { mutableStateOf(false) }
    var filterState by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MainBackground)
                    .padding(top = paddingValues.calculateTopPadding()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AimsBox(
                    aimList = budgetsState.aims,
                    onClick = { showAimDialog = true },
                    currency = globalState.currentAccount?.currency ?: Currency.RUB
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(1f)
                        .clip(RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp))
                        .background(SecondaryBackground),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = "Бюджеты",
                        color = White,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 23.sp,
                        modifier = Modifier.padding(top = 18.dp)
                    )
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        FilterButton {
                            budgetsViewModel.filter(filterState)
                            filterState = !filterState
                        }
                        AddButton(true) {
                            showBudgetDialog = true
                        }
                    }

                    Icon(
                        modifier = Modifier.padding(top = 2.dp, bottom = 3.dp),
                        painter = painterResource(id = R.drawable.white_line),
                        contentDescription = "divisor",
                        tint = Color.White,
                    )

                    BudgetsList(budgetsList = budgetsState.budgets, currency = globalState.currentAccount?.currency ?: Currency.RUB )
                }
            }
        }
    )

    if (showBudgetDialog) {
        BudgetDialog(onDismiss = { showBudgetDialog = false })
    }

    if (showAimDialog) {
        AimDialog(onDismiss = { showAimDialog = false })
    }
}
