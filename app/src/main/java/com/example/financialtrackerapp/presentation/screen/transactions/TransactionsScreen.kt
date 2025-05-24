package com.example.financialtrackerapp.presentation.screen.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.financialtrackerapp.presentation.screen.main.GlobalViewModel
import com.example.financialtrackerapp.presentation.ui.components.BalanceBox
import com.example.financialtrackerapp.presentation.ui.components.ExpenseBox
import com.example.financialtrackerapp.presentation.ui.components.IncomeBox
import com.example.financialtrackerapp.presentation.ui.components.TransactionsList
import com.example.financialtrackerapp.presentation.ui.theme.MainBackground
import com.example.financialtrackerapp.presentation.ui.theme.SecondaryBackground
import com.example.financialtrackerapp.presentation.ui.theme.White
import com.example.financialtrackerapp.presentation.ui.theme.poppinsFontFamily

@Composable
fun TransactionsScreen(
    transactionsViewModel: TransactionsViewModel = hiltViewModel(),
    globalViewModel: GlobalViewModel
) {
    val transactionsState = transactionsViewModel.transactionsState.collectAsState()
    val globalState by globalViewModel.globalState.collectAsState()

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
                BalanceBox(
                    currentBalance = globalState.currentAccount?.balance ?: 0.0,
                    currency = globalState.currentAccount?.currency ?: Currency.USD,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IncomeBox(
                        incomeAmount = transactionsState.value.incomeValue,
                        currency = globalState.currentAccount?.currency ?: Currency.USD,
                    )

                    ExpenseBox(
                        expenseAmount = transactionsState.value.expenseValue,
                        currency = globalState.currentAccount?.currency ?: Currency.USD,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

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
                        text = "Transactions",
                        color = White,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 23.sp,
                        modifier = Modifier.padding(top = 18.dp)
                    )

                    Icon(
                        modifier = Modifier.padding(top = 5.dp),
                        painter = painterResource(id = R.drawable.white_line),
                        contentDescription = "divisor",
                        tint = Color.White,
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    TransactionsList(
                        transactionsList = transactionsState.value.transactionList,
                        currency = globalState.currentAccount?.currency ?: Currency.USD
                    )
                }
            }
        }
    )
}
