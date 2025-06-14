package com.example.financialtrackerapp.presentation.ui.components

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.financialtrackerapp.R
import com.example.financialtrackerapp.domain.model.enums.TransactionType
import com.example.financialtrackerapp.presentation.ui.theme.Black
import com.example.financialtrackerapp.presentation.ui.theme.SecondaryBackground
import com.example.financialtrackerapp.presentation.ui.theme.SpecificOrange
import com.example.financialtrackerapp.presentation.ui.theme.White
import com.example.financialtrackerapp.presentation.ui.theme.poppinsFontFamily
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener

@Composable
fun PieChartView(
    modifier: Modifier = Modifier,
    dataMap: Map<String, Float>,
    colors: List<Int>,
) {
    AndroidView(
        factory = { context ->
            PieChart(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                isDrawHoleEnabled = true
                setHoleColor(Color.TRANSPARENT)
                setTransparentCircleColor(Color.TRANSPARENT)
                setTransparentCircleAlpha(0)
                transparentCircleRadius = 0f

                description.isEnabled = false
                isRotationEnabled = true
                setUsePercentValues(true)
                setDrawEntryLabels(false)
                setEntryLabelColor(Color.BLACK)
                legend.isEnabled = false

                setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                    override fun onValueSelected(e: Entry?, h: Highlight?) {
                        Toast.makeText(
                            context,
                            "You chose: ${(e as? PieEntry)?.label}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onNothingSelected() {}
                })
            }
        },
        modifier = modifier,
        update = { chart ->
            val entries = dataMap.map { (label, value) ->
                PieEntry(value, label)
            }

            val dataSet = PieDataSet(entries, "").apply {
                setColors(colors)
                valueTextSize = 14f
                valueTextColor = Color.WHITE
                sliceSpace = 2f
                valueFormatter = object : ValueFormatter() {
                    @SuppressLint("DefaultLocale")
                    override fun getFormattedValue(value: Float): String {
                        return if (value < 5f) {
                            ""
                        } else {
                            return if (value % 1f < 0.01f || value % 1f > 0.99f) {
                                value.toInt().toString() + "%"
                            } else {
                                String.format("%.1f%%", value)
                            }
                        }
                    }
                }
            }

            chart.data = PieData(dataSet)
            chart.invalidate()
            chart.animateY(1000, Easing.EaseInOutQuad)
        }
    )
}

@Composable
fun BarChartView(
    modifier: Modifier = Modifier,
    dataMap: Map<String, Float>,
    colors: List<Int>,
) {
    AndroidView(
        factory = { context ->
            BarChart(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                description.isEnabled = false
                legend.isEnabled = false

                setExtraOffsets(10f, 10f, 10f, 10f)

                axisLeft.isEnabled = false
                axisRight.isEnabled = false
                xAxis.isEnabled = false

                setDrawValueAboveBar(true)

                setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                    override fun onValueSelected(e: Entry?, h: Highlight?) {
                        Toast.makeText(
                            context,
                            "You chose: ${(e as? BarEntry)?.x}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onNothingSelected() {}
                })
            }
        },
        modifier = modifier,
        update = { chart ->
            val entries = dataMap.toList().mapIndexed { index, (_, value) ->
                BarEntry(index.toFloat(), value)
            }

            val dataSet = BarDataSet(entries, "").apply {
                setColors(colors)
                valueTextSize = 14f
                valueTextColor = Color.WHITE
                valueFormatter = object : ValueFormatter() {
                    @SuppressLint("DefaultLocale")
                    override fun getFormattedValue(value: Float): String {
                        return if (value < 5f) {
                            ""
                        } else {
                            String.format("%.1f", value)
                        }
                    }
                }
            }

            chart.data = BarData(dataSet)
            chart.invalidate()
        }
    )
}

@Composable
fun ToggleTransactionTypeButton(
    selectedType: TransactionType,
    onToggle: () -> Unit
) {
    val isExpense = selectedType == TransactionType.EXPENSE

    Button(
        onClick = onToggle,
        shape = RoundedCornerShape(22.dp),
        colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.ui.graphics.Color.Transparent),
        contentPadding = PaddingValues(),
    ) {
        Row(
            modifier = Modifier
                .background(
                    if (isExpense) SpecificOrange else SecondaryBackground,
                    shape = RoundedCornerShape(22.dp)
                )
                .padding(horizontal = 16.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = if (isExpense) "Расходы" else "Доходы",
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                color = if (isExpense) Black else White
            )
            Icon(
                painter = painterResource(R.drawable.ic_gap),
                contentDescription = "Options",
                tint = if (isExpense) Black else White
            )
        }

    }
}

@Composable
fun ToggleChartTypeButton(
    selectedChartType: ChartType,
    onToggle: (ChartType) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = { onToggle(ChartType.PIE) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_analysis),
                contentDescription = "Pie Chart",
                tint = if (selectedChartType == ChartType.PIE) SpecificOrange else Gray,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        IconButton(onClick = { onToggle(ChartType.BAR) }) {
            Icon(
                painter = painterResource(id = R.drawable.bar_graph),
                contentDescription = "Bar Chart",
                tint = if (selectedChartType == ChartType.BAR) SpecificOrange else Gray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

enum class ChartType {
    PIE,
    BAR
}