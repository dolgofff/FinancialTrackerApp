package com.example.financialtrackerapp.presentation.screen.dialogues.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.financialtrackerapp.domain.model.enums.TransactionType
import com.example.financialtrackerapp.presentation.ui.components.ExpenseParameters
import com.example.financialtrackerapp.presentation.ui.components.IncomeParameters
import com.example.financialtrackerapp.presentation.ui.components.TransferParameters
import com.example.financialtrackerapp.presentation.ui.components.TripleChoiceButton
import com.example.financialtrackerapp.presentation.ui.components.parseFormattedNumber
import com.example.financialtrackerapp.presentation.ui.theme.BottomBarColor

@Composable

fun NewTransactionDialog(
    newTransactionViewModel: NewTransactionViewModel = hiltViewModel(),
    onDismiss: () -> Unit
) {
    val transactionState = newTransactionViewModel.transactionState.collectAsState()

    Dialog(onDismissRequest = {
        newTransactionViewModel.resetState()
        onDismiss()
    }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(562.dp)
                .clip(RoundedCornerShape(46.dp))
                .background(BottomBarColor),
            contentAlignment = Alignment.TopCenter
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                TripleChoiceButton(
                    selectedType = transactionState.value.selectedType,
                    onTypeSelected = { newTransactionViewModel.updateSelectedType(it) }
                )
            }

            when (transactionState.value.selectedType) {
                TransactionType.EXPENSE -> ExpenseParameters(
                    state = transactionState.value,
                    onAmountChange = { newTransactionViewModel.updateAmount(parseFormattedNumber(it)) },
                    onCategoryChange = newTransactionViewModel::updateCategory,
                    onNoteChange = newTransactionViewModel::updateNote,
                    onPlaceChange = newTransactionViewModel::updatePlace,
                    onSubmit = {
                        newTransactionViewModel.createNewTransaction()
                        onDismiss()
                    }
                )

                TransactionType.INCOME -> IncomeParameters(
                    state = transactionState.value,
                    onAmountChange = { newTransactionViewModel.updateAmount(parseFormattedNumber(it)) },
                    onCategoryChange = newTransactionViewModel::updateCategory,
                    onNoteChange = newTransactionViewModel::updateNote,
                    onSubmit = {
                        newTransactionViewModel.createNewTransaction()
                        onDismiss()
                    }
                )

                TransactionType.TRANSFER -> TransferParameters(
                    state = transactionState.value,
                    onAmountChange = { newTransactionViewModel.updateAmount(parseFormattedNumber(it)) },
                    onNoteChange = newTransactionViewModel::updateNote,
                    onSubmit = {
                        newTransactionViewModel.createNewTransaction()
                        onDismiss()
                    }
                )
            }
        }
    }
}