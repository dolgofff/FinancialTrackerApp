package com.example.financialtrackerapp.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financialtrackerapp.R
import com.example.financialtrackerapp.domain.model.Transaction
import com.example.financialtrackerapp.domain.model.enums.Currency
import com.example.financialtrackerapp.presentation.ui.theme.SecondaryBackground
import com.example.financialtrackerapp.presentation.ui.theme.White
import com.example.financialtrackerapp.presentation.ui.theme.poppinsFontFamily

@Composable
fun BalanceBox(currentBalance: Double, currency: Currency, modifier: Modifier = Modifier) {
    val formattedBalance = formatNumber(currentBalance)

    Card(
        modifier = modifier.size(width = 415.dp, height = 80.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SecondaryBackground),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "Total Balance",
                fontSize = 20.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                color = White
            )
            Text(
                text = "${currencyToSymbol(currency)}${formattedBalance}",
                fontSize = 21.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                color = White
            )
        }
    }
}

@Composable
fun IncomeBox(incomeAmount: Double, currency: Currency) {
    val formattedBalance = formatNumber(incomeAmount)

    Card(
        modifier = Modifier
            .size(width = 160.dp, height = 92.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SecondaryBackground),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 2.dp)
                    .size(width = 25.dp, height = 25.dp),
                painter = painterResource(id = R.drawable.img_incomes),
                contentDescription = "incomes logo",
            )

            Text(
                text = "Income", fontSize = 18.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                color = White
            )

            Text(
                modifier = Modifier.padding(bottom = 2.dp),
                text = "${currencyToSymbol(currency)}${formattedBalance}",
                fontSize = 20.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = White
            )
        }
    }
}

@Composable
fun ExpenseBox(expenseAmount: Double, currency: Currency) {
    val formattedBalance = formatNumber(expenseAmount)

    Card(
        modifier = Modifier
            .size(width = 160.dp, height = 92.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SecondaryBackground),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 2.dp)
                    .size(width = 25.dp, height = 25.dp),
                painter = painterResource(id = R.drawable.img_expenses),
                contentDescription = "expenses logo",
            )

            Text(
                text = "Expense", fontSize = 18.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                color = White
            )

            Text(
                modifier = Modifier.padding(bottom = 2.dp),
                text = "${currencyToSymbol(currency)}${formattedBalance}",
                fontSize = 20.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = White
            )
        }
    }
}

@Composable
fun TransactionsList(transactionsList: List<Transaction>, currency: Currency) {
    val groupedTransactions = transactionsList.groupBy { it.date }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        groupedTransactions.forEach { (date, transactions) ->
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = formatTransactionDate(date),
                        fontSize = 21.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = White
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    transactions.forEach { transaction ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Icon(
                                painter = painterResource(id = categoryToIcon(transaction.category)),
                                contentDescription = "Category Icon",
                                tint = Color.Unspecified,
                                modifier = Modifier.padding(end = 2.dp)
                            )

                            Text(
                                modifier = Modifier.padding(start = 3.dp),
                                text = categoryToTitle(transaction.category),
                                fontSize = 18.sp,
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.Medium,
                                color = White
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "${currencyToSymbol(currency)}${formatNumber(transaction.amount)}",
                                fontSize = 18.sp,
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.Medium,
                                color = transactionTypeToColor(transaction.type)
                            )
                        }
                    }
                }
            }
        }
    }
}



