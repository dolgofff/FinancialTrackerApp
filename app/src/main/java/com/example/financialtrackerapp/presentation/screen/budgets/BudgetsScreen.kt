package com.example.financialtrackerapp.presentation.screen.budgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financialtrackerapp.R
import com.example.financialtrackerapp.domain.model.Aim
import com.example.financialtrackerapp.domain.model.Budget
import com.example.financialtrackerapp.domain.model.enums.Category
import com.example.financialtrackerapp.presentation.ui.components.AddButton
import com.example.financialtrackerapp.presentation.ui.components.AimsBox
import com.example.financialtrackerapp.presentation.ui.components.BudgetsList
import com.example.financialtrackerapp.presentation.ui.components.FilterButton
import com.example.financialtrackerapp.presentation.ui.theme.MainBackground
import com.example.financialtrackerapp.presentation.ui.theme.SecondaryBackground
import com.example.financialtrackerapp.presentation.ui.theme.White
import com.example.financialtrackerapp.presentation.ui.theme.poppinsFontFamily

@Composable
@Preview(showBackground = true)
fun BudgetsScreen() {
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

                val testAims = listOf(
                    Aim(
                        id = 1,
                        accountId = 101,
                        name = "New phone",
                        targetAmount = 1000.0,
                        savedAmount = 856.0
                    )
                )

                AimsBox(aimList = testAims)

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
                        text = "Budgets",
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
                        FilterButton { }
                        AddButton(true) { }
                    }

                    Icon(
                        modifier = Modifier.padding(top = 2.dp, bottom = 3.dp),
                        painter = painterResource(id = R.drawable.white_line),
                        contentDescription = "divisor",
                        tint = Color.White,
                    )

                    val testBudgets = listOf(
                        Budget(
                            id = 1L,
                            accountId = 101L,
                            category = Category.PRODUCTS,
                            spentAmount = 290.0,
                            limitAmount = 300.0,
                            startDate = 1719830400000,
                            endDate = 1722422400000
                        ),
                        Budget(
                            id = 2L,
                            accountId = 101L,
                            category = Category.ENTERTAINMENTS,
                            spentAmount = 100.0,
                            limitAmount = 150.0,
                            startDate = 1719830400000,
                            endDate = 1722422400000
                        ),
                        Budget(
                            id = 3L,
                            accountId = 101L,
                            category = Category.PUBLIC_TRANSPORT,
                            spentAmount = 20.0,
                            limitAmount = 100.0,
                            startDate = 1719830400000,
                            endDate = 1722422400000
                        ),
                        Budget(
                            id = 4L,
                            accountId = 101L,
                            category = Category.MEDICINE,
                            spentAmount = 100.0,
                            limitAmount = 200.0,
                            startDate = 1719830400000,
                            endDate = 1722422400000
                        )
                    )

                    BudgetsList(testBudgets)


                }
            }
        }
    )
}

