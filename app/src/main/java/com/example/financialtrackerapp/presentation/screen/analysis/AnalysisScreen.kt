package com.example.financialtrackerapp.presentation.screen.analysis

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.financialtrackerapp.R
import com.example.financialtrackerapp.domain.model.enums.Currency
import com.example.financialtrackerapp.presentation.screen.main.GlobalViewModel
import com.example.financialtrackerapp.presentation.ui.components.BarChartView
import com.example.financialtrackerapp.presentation.ui.components.ChartType
import com.example.financialtrackerapp.presentation.ui.components.PieChartView
import com.example.financialtrackerapp.presentation.ui.components.ToggleChartTypeButton
import com.example.financialtrackerapp.presentation.ui.components.ToggleTransactionTypeButton
import com.example.financialtrackerapp.presentation.ui.components.categoryToIcon
import com.example.financialtrackerapp.presentation.ui.components.categoryToRTitle
import com.example.financialtrackerapp.presentation.ui.components.currencyToSymbol
import com.example.financialtrackerapp.presentation.ui.components.formatNumber
import com.example.financialtrackerapp.presentation.ui.components.stringToCategory
import com.example.financialtrackerapp.presentation.ui.components.transactionTypeToColor
import com.example.financialtrackerapp.presentation.ui.theme.MainBackground
import com.example.financialtrackerapp.presentation.ui.theme.SecondaryBackground
import com.example.financialtrackerapp.presentation.ui.theme.White
import com.example.financialtrackerapp.presentation.ui.theme.poppinsFontFamily

@Composable
fun AnalysisScreen(
    analysisViewModel: AnalysisViewModel = hiltViewModel(),
    globalViewModel: GlobalViewModel
) {
    val analysisState by analysisViewModel.analysisState.collectAsState()
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
                Text(
                    text = "Статистика",
                    color = White,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 23.sp,
                    modifier = Modifier.padding(top = 18.dp)
                )

                val dividerPainter = painterResource(id = R.drawable.white_line)

                Icon(
                    modifier = Modifier.padding(top = 5.dp),
                    painter = dividerPainter,
                    contentDescription = "divisor",
                    tint = Color.White,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ToggleChartTypeButton(
                        selectedChartType = analysisState.selectedChartType,
                        onToggle = { analysisViewModel.setChartType(it) }
                    )

                    ToggleTransactionTypeButton(
                        selectedType = analysisState.selectedType,
                        onToggle = { analysisViewModel.toggleTransactionType() }
                    )
                }

                when (analysisState.selectedChartType) {
                    ChartType.PIE -> PieChartView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        dataMap = analysisState.categorySums,
                        colors = analysisState.colors
                    )

                    ChartType.BAR -> BarChartView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        dataMap = analysisState.categorySums,
                        colors = analysisState.colors
                    )
                }

                Icon(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                    painter = dividerPainter,
                    contentDescription = "divisor",
                    tint = Color.White,
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
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        items(analysisState.categorySums.toList()) { (category, amount) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(
                                        id = categoryToIcon(stringToCategory(category))
                                    ),
                                    contentDescription = "Category Icon",
                                    tint = Color.Unspecified,
                                    modifier = Modifier.padding(end = 2.dp)
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = categoryToRTitle(stringToCategory(category)) /*category*/,
                                    fontSize = 20.sp,
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Medium,
                                    color = White,
                                    modifier = Modifier.weight(1f)
                                )

                                Text(
                                    text = "${currencyToSymbol(globalState.currentAccount?.currency ?: Currency.RUB)} ${formatNumber(amount.toDouble())}",
                                    fontSize = 20.sp,
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Medium,
                                    textAlign = TextAlign.End,
                                    color = transactionTypeToColor(analysisState.selectedType)
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

