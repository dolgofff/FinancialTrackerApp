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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.financialtrackerapp.R
import com.example.financialtrackerapp.domain.model.enums.TransactionType
import com.example.financialtrackerapp.presentation.ui.components.BarChartView
import com.example.financialtrackerapp.presentation.ui.components.ChartType
import com.example.financialtrackerapp.presentation.ui.components.PieChartView
import com.example.financialtrackerapp.presentation.ui.components.ToggleChartTypeButton
import com.example.financialtrackerapp.presentation.ui.components.ToggleTransactionTypeButton
import com.example.financialtrackerapp.presentation.ui.components.categoryToIcon
import com.example.financialtrackerapp.presentation.ui.components.formatNumber
import com.example.financialtrackerapp.presentation.ui.components.stringToCategory
import com.example.financialtrackerapp.presentation.ui.components.transactionTypeToColor
import com.example.financialtrackerapp.presentation.ui.theme.MainBackground
import com.example.financialtrackerapp.presentation.ui.theme.SecondaryBackground
import com.example.financialtrackerapp.presentation.ui.theme.White
import com.example.financialtrackerapp.presentation.ui.theme.poppinsFontFamily

@Composable
@Preview(showBackground = true)
fun AnalysisScreen(analysisViewModel: AnalysisViewModel = hiltViewModel()) {
    val selectedType by analysisViewModel.selectedType.collectAsState()
    var dataMap by remember { mutableStateOf<Map<String, Float>>(emptyMap()) }
    var colors by remember { mutableStateOf<List<Int>>(emptyList()) }
    val selectedChartType by analysisViewModel.selectedChartType

    LaunchedEffect(selectedType) {
        val (map, colorList) = analysisViewModel.getCategorySums()
        dataMap = map
        colors = colorList
    }

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
                    text = "Detailed Analysis",
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ToggleChartTypeButton(
                        selectedChartType = selectedChartType,
                        onToggle = { analysisViewModel.switchType(it) }
                    )

                    ToggleTransactionTypeButton(
                        selectedType = selectedType,
                        onToggle = { analysisViewModel.switchType() }
                    )
                }

                when (selectedChartType) {
                    ChartType.PIE -> PieChartView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        dataMap = dataMap,
                        colors = colors
                    )

                    ChartType.BAR -> BarChartView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        dataMap = dataMap,
                        colors = colors
                    )
                }

                Icon(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                    painter = painterResource(id = R.drawable.white_line),
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
                        items(dataMap.toList()) { (category, amount) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(
                                        id = categoryToIcon(
                                            stringToCategory(
                                                category
                                            )
                                        )
                                    ),
                                    contentDescription = "Category Icon",
                                    tint = Color.Unspecified,
                                    modifier = Modifier.padding(end = 2.dp)
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = category,
                                    fontSize = 20.sp,
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Medium,
                                    color = White,
                                    modifier = Modifier.weight(1f)
                                )

                                Text(
                                    text = "₽${formatNumber(amount.toDouble())}",
                                    fontSize = 20.sp,
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Medium,
                                    textAlign = TextAlign.End,
                                    color = transactionTypeToColor(selectedType)
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

