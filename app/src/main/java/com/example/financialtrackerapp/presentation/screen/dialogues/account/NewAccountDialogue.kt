package com.example.financialtrackerapp.presentation.screen.dialogues.account

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
import com.example.financialtrackerapp.presentation.screen.main.GlobalViewModel
import com.example.financialtrackerapp.presentation.ui.components.AccountTypeDropdown
import com.example.financialtrackerapp.presentation.ui.components.CurrencyDropdown
import com.example.financialtrackerapp.presentation.ui.components.InitialBalanceInputField
import com.example.financialtrackerapp.presentation.ui.components.NameInputField
import com.example.financialtrackerapp.presentation.ui.components.SubmissionButton
import com.example.financialtrackerapp.presentation.ui.components.parseFormattedNumber
import com.example.financialtrackerapp.presentation.ui.theme.BottomBarColor
import com.example.financialtrackerapp.presentation.ui.theme.White
import com.example.financialtrackerapp.presentation.ui.theme.poppinsFontFamily

@Composable
fun NewAccountDialogue(
    onDismiss: () -> Unit,
    newAccountViewModel: NewAccountViewModel = hiltViewModel(),
    globalViewModel: GlobalViewModel,
) {
    val accountState by newAccountViewModel.accountState.collectAsState()

    Dialog(
        onDismissRequest = {
            newAccountViewModel.resetState()
            onDismiss()
        }) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .clip(RoundedCornerShape(46.dp))
                .background(BottomBarColor),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 12.dp),
                text = "New Account",
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
                NameInputField(
                    name = accountState.name,
                    onNameChange = { newAccountViewModel.updateName(it) })

                AccountTypeDropdown(
                    accountType = accountState.type,
                    onTypeChange = { newAccountViewModel.updateType(it) })

                CurrencyDropdown(
                    selectedCurrency = accountState.currency,
                    onCurrencyChange = {
                        newAccountViewModel.updateCurrency(it)
                    })

                InitialBalanceInputField(
                    balance = accountState.initialBalance,
                    onBalanceChange = {
                        newAccountViewModel.updateInitialBalance(
                            parseFormattedNumber(it)
                        )
                    }
                )

                Spacer(modifier = Modifier.size(72.dp))

                SubmissionButton(
                    title = "Create an account",
                    onClick = {
                        newAccountViewModel.createNewAccount()
                    }
                )
            }
        }
    }

    LaunchedEffect(accountState.isCreated) {
        if (accountState.isCreated) {
            accountState.id?.let { id ->
                globalViewModel.updateCurrentAccount(id)
                newAccountViewModel.clear()
                onDismiss()
            }
        }
    }
}
