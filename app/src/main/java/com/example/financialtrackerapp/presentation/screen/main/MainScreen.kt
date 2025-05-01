package com.example.financialtrackerapp.presentation.screen.main

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.financialtrackerapp.presentation.navigation.BottomItem
import com.example.financialtrackerapp.presentation.navigation.destinations.AdvicesScreenObject
import com.example.financialtrackerapp.presentation.navigation.destinations.AnalysisScreenObject
import com.example.financialtrackerapp.presentation.navigation.destinations.BudgetsScreenObject
import com.example.financialtrackerapp.presentation.navigation.destinations.LoginScreenObject
import com.example.financialtrackerapp.presentation.navigation.destinations.RegistrationScreenObject
import com.example.financialtrackerapp.presentation.navigation.destinations.TransactionsScreenObject
import com.example.financialtrackerapp.presentation.screen.advices.AdvicesScreen
import com.example.financialtrackerapp.presentation.screen.analysis.AnalysisScreen
import com.example.financialtrackerapp.presentation.screen.budgets.BudgetsScreen
import com.example.financialtrackerapp.presentation.screen.dialogues.menu.AccountsMenu
import com.example.financialtrackerapp.presentation.screen.dialogues.transaction.NewTransactionDialog
import com.example.financialtrackerapp.presentation.screen.login.LoginScreen
import com.example.financialtrackerapp.presentation.screen.registration.RegistrationScreen
import com.example.financialtrackerapp.presentation.screen.transactions.TransactionsScreen
import com.example.financialtrackerapp.presentation.ui.theme.BottomBarColor
import com.example.financialtrackerapp.presentation.ui.theme.NegativeBalance
import com.example.financialtrackerapp.presentation.ui.theme.SpecificOrange
import com.example.financialtrackerapp.presentation.ui.theme.White
import com.example.financialtrackerapp.presentation.ui.theme.poppinsFontFamily

@Composable
fun MainScreen(isAuthenticated: Boolean?) {
    val navController = rememberNavController()
    var showTransactionDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            if (isAuthenticated == true) {
                BottomNavigationBar(
                    navController = navController,
                    onCreateTransactionClick = { showTransactionDialog = true }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = when (isAuthenticated) {
                true -> AnalysisScreenObject
                false -> LoginScreenObject
                null -> LoginScreenObject
            }
        ) {
            composable<LoginScreenObject> { LoginScreen(navController) }
            composable<RegistrationScreenObject> { RegistrationScreen(navController) }
            composable<TransactionsScreenObject> { TransactionsScreen() }
            composable<AnalysisScreenObject> { AnalysisScreen() }
            composable<BudgetsScreenObject> { BudgetsScreen() }
            composable<AdvicesScreenObject> { AdvicesScreen() }
        }

        if (isAuthenticated == true) {
            AccountsMenu()
        }

        if (showTransactionDialog) {
            NewTransactionDialog(
                onDismiss = { showTransactionDialog = false }
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavController,
    onCreateTransactionClick: () -> Unit
) {
    val listItems = listOf(
        TransactionsScreenObject to BottomItem.TransactionsScreen,
        AnalysisScreenObject to BottomItem.AnalysisScreen,
        null to BottomItem.TransactionsDialogue, // диалог (иконка посередине)
        BudgetsScreenObject to BottomItem.BudgetsScreen,
        AdvicesScreenObject to BottomItem.AdvicesScreen,
    )

    NavigationBar(containerColor = BottomBarColor) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = backStackEntry?.destination

        listItems.forEach { (item, itemInfo) ->
            val isTransactionDialog = itemInfo is BottomItem.TransactionsDialogue

            NavigationBarItem(
                selected = !isTransactionDialog && item != null && currentDestination?.hierarchy?.any {
                    it.hasRoute(item::class)
                } == true,
                onClick = {
                    if (isTransactionDialog) {
                        onCreateTransactionClick()
                    } else if (item != null && currentDestination?.hasRoute(item::class) != true) {
                        navController.navigate(item)
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = itemInfo.iconId),
                        contentDescription = itemInfo.title,
                       // tint = if (isTransactionDialog) SpecificOrange else Color.Unspecified
                    )
                },
                label = {
                    if (itemInfo.title.isNotEmpty()) {
                        Text(
                            text = itemInfo.title,
                            fontSize = 12.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                        )
                    }
                },
                colors = if (isTransactionDialog) {
                    NavigationBarItemDefaults.colors(
                        selectedIconColor = NegativeBalance,
                        unselectedIconColor = NegativeBalance,
                        selectedTextColor = NegativeBalance,
                        unselectedTextColor = NegativeBalance,
                        indicatorColor = Color.Transparent
                    )
                } else {
                    NavigationBarItemDefaults.colors(
                        selectedIconColor = SpecificOrange,
                        unselectedIconColor = White,
                        selectedTextColor = SpecificOrange,
                        unselectedTextColor = White,
                        indicatorColor = Color.Transparent
                    )
                }
            )
        }
    }
}