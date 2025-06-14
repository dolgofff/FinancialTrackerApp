package com.example.financialtrackerapp.presentation.screen.dialogues.aim

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
import com.example.financialtrackerapp.domain.model.Aim
import com.example.financialtrackerapp.presentation.ui.components.SpecialAmountInputField
import com.example.financialtrackerapp.presentation.ui.components.SubmissionButton
import com.example.financialtrackerapp.presentation.ui.components.parseFormattedNumber
import com.example.financialtrackerapp.presentation.ui.theme.BottomBarColor
import com.example.financialtrackerapp.presentation.ui.theme.White
import com.example.financialtrackerapp.presentation.ui.theme.poppinsFontFamily

@Composable
fun SetAmountDialog(
    onDismiss: () -> Unit,
    currentAim: Aim,
    setAmountViewModel: SetAmountViewModel = hiltViewModel(),
) {
    val aimState by setAmountViewModel.setState.collectAsState()

    Dialog(
        onDismissRequest = {
            setAmountViewModel.clear()
            onDismiss()
        }) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(46.dp))
                .background(BottomBarColor),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 4.dp),
                text = "Добавить",
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
                Spacer(modifier = Modifier.height(42.dp))

                SpecialAmountInputField(
                    label = "Сумма",
                    showDivisor = true,
                    amount = aimState.savedAmount,
                    onAmountChange = {
                        setAmountViewModel.updateSavedAmount(
                            parseFormattedNumber(it)
                        )
                    }
                )

                Spacer(modifier = Modifier.weight(0.8f))

                SubmissionButton(
                    title = "Обновить",
                    onClick = {
                        setAmountViewModel.updateAim(currentAim)
                    }
                )

                Spacer(modifier = Modifier.size(32.dp))
            }

        }
    }

    LaunchedEffect(aimState.isDone) {
        if (aimState.isDone) {
            setAmountViewModel.clear()
            onDismiss()
        }
    }
}