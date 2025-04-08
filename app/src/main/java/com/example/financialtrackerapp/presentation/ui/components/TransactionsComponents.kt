package com.example.financialtrackerapp.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financialtrackerapp.R
import com.example.financialtrackerapp.domain.model.Transaction
import com.example.financialtrackerapp.presentation.ui.theme.SecondaryBackground
import com.example.financialtrackerapp.presentation.ui.theme.White
import com.example.financialtrackerapp.presentation.ui.theme.poppinsFontFamily

@Composable
fun BalanceBox(currentBalance: Double, modifier: Modifier = Modifier) {
    val formattedBalance = formatNumber(currentBalance)

    Card(
        modifier = modifier.size(width = 357.dp, height = 75.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SecondaryBackground),
        elevation = CardDefaults.cardElevation(1.dp)
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
                fontSize = 19.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                color = White
            )

            Text(
                text = "$${formattedBalance}",
                fontSize = 20.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                color = White
            )
        }
    }
}

@Composable
fun IncomeBox(incomeAmount: Double) {
    val formattedBalance = formatNumber(incomeAmount)

    Card(
        modifier = Modifier
            .size(width = 150.dp, height = 92.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SecondaryBackground),
        elevation = CardDefaults.cardElevation(1.dp)
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
                text = "Income", fontSize = 17.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                color = White
            )

            Text(
                modifier = Modifier.padding(bottom = 2.dp),
                text = "$${formattedBalance}",
                fontSize = 19.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = White
            )
        }
    }
}

@Composable
fun ExpenseBox(expenseAmount: Double) {
    val formattedBalance = formatNumber(expenseAmount)

    Card(
        modifier = Modifier
            .size(width = 150.dp, height = 92.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SecondaryBackground),
        elevation = CardDefaults.cardElevation(1.dp)
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
                text = "Expense", fontSize = 17.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                color = White
            )

            Text(
                modifier = Modifier.padding(bottom = 2.dp),
                text = "$${formattedBalance}",
                fontSize = 19.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = White
            )
        }
    }
}

//TODO: ФИЛЬТР ПО ДАТАМ
@Composable
fun TransactionsList(transactionsList: List<Transaction>) {
    LazyColumn(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(
            transactionsList
        ) { _, item ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = formatTransactionDate(item.date),
                    fontSize = 15.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = White
                )
                for (transaction in transactionsList) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            painter = painterResource(id = categoryToIcon(transaction.category)),
                            contentDescription = "Category Icon",
                            tint = Color.Transparent
                        )

                        Text(
                            modifier = Modifier.padding(start = 3.dp, end = 52.dp),
                            text = categoryToTitle(transaction.category),
                            fontSize = 15.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                            color = White
                        )

                        Text(
                            text = formatNumber(transaction.amount),
                            fontSize = 15.sp,
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



