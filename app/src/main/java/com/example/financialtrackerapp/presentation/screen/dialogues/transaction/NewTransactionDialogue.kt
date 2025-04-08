package com.example.financialtrackerapp.presentation.screen.dialogues.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.financialtrackerapp.presentation.ui.components.ExpenseParameters
import com.example.financialtrackerapp.presentation.ui.components.TripleChoiceButton
import com.example.financialtrackerapp.presentation.ui.theme.SecondaryBackground

@Composable
@Preview
fun NewTransactionDialog() {
    Dialog(onDismissRequest = {}) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(562.dp)
                .clip(RoundedCornerShape(46.dp))
                .background(SecondaryBackground),
            contentAlignment = Alignment.TopCenter
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TripleChoiceButton()
            }

            ExpenseParameters()
        }
    }
}