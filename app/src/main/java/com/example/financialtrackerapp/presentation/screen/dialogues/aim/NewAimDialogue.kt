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
import androidx.compose.foundation.layout.width
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
import com.example.financialtrackerapp.presentation.ui.components.NameInputField
import com.example.financialtrackerapp.presentation.ui.components.SpecialAmountInputField
import com.example.financialtrackerapp.presentation.ui.components.SubmissionButton
import com.example.financialtrackerapp.presentation.ui.components.parseFormattedNumber
import com.example.financialtrackerapp.presentation.ui.theme.BottomBarColor
import com.example.financialtrackerapp.presentation.ui.theme.White
import com.example.financialtrackerapp.presentation.ui.theme.poppinsFontFamily

@Composable
fun AimDialog(
    onDismiss: () -> Unit,
    aimViewModel: NewAimViewModel = hiltViewModel(),
) {
    val aimState by aimViewModel.aimState.collectAsState()

    Dialog(
        onDismissRequest = {
            aimViewModel.clear()
            onDismiss()
        }) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(440.dp)
                .clip(RoundedCornerShape(46.dp))
                .background(BottomBarColor),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 12.dp),
                text = "Новая цель",
                fontSize = 24.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = White,
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.align(Alignment.Center),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                NameInputField(
                    name = aimState.name,
                    onNameChange = { aimViewModel.updateName(it) })

                SpecialAmountInputField(
                    label = "Сумма",
                    showDivisor = false,
                    amount = aimState.targetAmount,
                    onAmountChange = {
                        aimViewModel.updateTargetAmount(
                            parseFormattedNumber(it)
                        )
                    }
                )

                SpecialAmountInputField(
                    label = "Сохранено",
                    showDivisor = true,
                    amount = aimState.savedAmount,
                    onAmountChange = {
                        aimViewModel.updateSavedAmount(
                            parseFormattedNumber(it)
                        )
                    }
                )

                Spacer(modifier = Modifier.size(72.dp))

                SubmissionButton(
                    title = "Создать цель",
                    onClick = {
                        aimViewModel.createAim()
                    }
                )
            }
        }
    }

    LaunchedEffect(aimState.isCreated) {
        if (aimState.isCreated) {
            aimViewModel.clear()
            onDismiss()
        }
    }
}