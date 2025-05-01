package com.example.financialtrackerapp.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financialtrackerapp.R
import com.example.financialtrackerapp.domain.model.Aim
import com.example.financialtrackerapp.domain.model.Budget
import com.example.financialtrackerapp.domain.model.enums.Category
import com.example.financialtrackerapp.presentation.ui.theme.LightGrey
import com.example.financialtrackerapp.presentation.ui.theme.MainBackground
import com.example.financialtrackerapp.presentation.ui.theme.PositiveBalance
import com.example.financialtrackerapp.presentation.ui.theme.SecondaryBackground
import com.example.financialtrackerapp.presentation.ui.theme.SpecificOrange
import com.example.financialtrackerapp.presentation.ui.theme.White
import com.example.financialtrackerapp.presentation.ui.theme.poppinsFontFamily


@Composable
fun AddButton(size: Boolean, onClick: () -> Unit) {
    IconButton(
        modifier = if (size) Modifier.padding(top = 3.dp) else Modifier.padding(top = 5.dp),
        onClick = onClick,
    ) {
        Icon(
            painter = if (size) painterResource(R.drawable.ic_add) else painterResource(R.drawable.ic_add_small),
            contentDescription = "Add new component",
            tint = Color.Unspecified
        )
    }
}

@Composable
fun FilterButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(22.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues()
    ) {
        Row(
            modifier = Modifier
                .background(SecondaryBackground, shape = RoundedCornerShape(22.dp))
                .padding(horizontal = 16.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Period",
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                color = White
            )

            Icon(
                painter = painterResource(R.drawable.ic_gap),
                contentDescription = "Options"
            )
        }
    }
}

@Composable
fun AimsBox(aimList: List<Aim>) {
    Card(
        modifier = Modifier.padding(16.dp),
        shape = RoundedCornerShape(60.dp),
        colors = CardDefaults.cardColors(containerColor = SecondaryBackground),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Aims",
                    fontSize = 23.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = White,
                    modifier = Modifier.weight(1f)
                )

                AddButton(false) {}
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(aimList) { aim ->
                    AimItem(aim = aim)
                }
            }
        }
    }
}

@Composable
fun BudgetsList(budgetsList: List<Budget>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
    ) {
        items(budgetsList) { budget ->
            BudgetItem(budget = budget)
        }
    }
}

@Composable
fun AimItem(aim: Aim) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .padding(horizontal = 2.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.ic_aim),
                contentDescription = "Aim icon",
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = aim.name,
                fontSize = 18.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                color = White
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(50))
                .background(White)
        ) {
            LinearProgressIndicator(
                progress = { (aim.savedAmount / aim.targetAmount).toFloat().coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxSize(),
                color = indicateProgressColor(aim.savedAmount, aim.targetAmount),
                trackColor = Color.Transparent
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 3.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "$${formatNumber(aim.savedAmount)} / $${formatNumber(aim.targetAmount)}",
                fontSize = 13.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                color = indicateProgressColor(aim.savedAmount, aim.targetAmount)
            )
        }
    }
}

@Composable
fun BudgetItem(budget: Budget) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(categoryToIcon(budget.category)),
                contentDescription = "Budget icon",
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = categoryToTitle(budget.category),
                fontSize = 18.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                color = White
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(7.dp)
                .clip(RoundedCornerShape(50))
                .background(White)
        ) {
            LinearProgressIndicator(
                progress = { (budget.spentAmount / budget.limitAmount).toFloat().coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxSize(),
                color = indicateBudgetProgressColor(budget.spentAmount, budget.limitAmount),
                trackColor = Color.Transparent
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 3.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "$${formatNumber(budget.spentAmount)} / $${formatNumber(budget.limitAmount)}",
                fontSize = 15.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                color = indicateBudgetProgressColor(budget.spentAmount, budget.limitAmount)
            )
        }
    }
}







