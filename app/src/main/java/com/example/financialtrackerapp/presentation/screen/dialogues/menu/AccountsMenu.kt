package com.example.financialtrackerapp.presentation.screen.dialogues.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.financialtrackerapp.domain.model.enums.AccountType
import com.example.financialtrackerapp.presentation.screen.dialogues.account.NewAccountDialogue
import com.example.financialtrackerapp.presentation.screen.main.GlobalViewModel
import com.example.financialtrackerapp.presentation.ui.components.accountTypeToIcon
import com.example.financialtrackerapp.presentation.ui.theme.SpecificOrange
import com.example.financialtrackerapp.presentation.ui.theme.poppinsFontFamily


@Composable
fun AccountsMenu(globalViewModel: GlobalViewModel = hiltViewModel()) {
    var expanded by remember { mutableStateOf(false) }
    var showAccountDialog by remember { mutableStateOf(false) }

    val globalState by globalViewModel.globalState.collectAsState()
    val usersAccounts = globalState.accountList.collectAsState(initial = emptyList())

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 8.dp,
                start = 16.dp
            )
            .zIndex(1f),
        contentAlignment = Alignment.TopStart
    ) {
        IconButton(onClick = { expanded = true }) {
            Icon(
                painter = painterResource(
                    id = accountTypeToIcon(globalState.currentAccount?.type ?: AccountType.SINGLE)
                ),
                contentDescription = "Account menu",
                tint = Color.Unspecified,
                modifier = Modifier.size(42.dp)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            usersAccounts.value.forEach { account ->
                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {
                            Icon(
                                painter = painterResource(accountTypeToIcon(account.type)),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(end = 12.dp)
                            )
                            Text(
                                text = account.name,
                                fontFamily = poppinsFontFamily,
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    },
                    onClick = {
                        globalViewModel.updateCurrentAccount(account)
                        expanded = false
                    }
                )
            }

            if (usersAccounts.value.isNotEmpty()) {
                HorizontalDivider()
            }

            DropdownMenuItem(
                onClick = {
                    expanded = false
                    showAccountDialog = true
                },
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Create new account",
                            tint = SpecificOrange,
                            modifier = Modifier
                                .padding(end = 12.dp)
                        )
                        Text(
                            text = "Create new account",
                            color = SpecificOrange,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            )
        }
    }

    if (showAccountDialog) {
        NewAccountDialogue(onDismiss = { showAccountDialog = false })
    }
}