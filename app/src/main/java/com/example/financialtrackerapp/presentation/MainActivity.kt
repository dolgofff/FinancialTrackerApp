package com.example.financialtrackerapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.financialtrackerapp.presentation.screen.main.GlobalViewModel
import com.example.financialtrackerapp.presentation.screen.main.MainScreen
import com.example.financialtrackerapp.presentation.ui.theme.FinancialTrackerAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val globalViewModel: GlobalViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                globalViewModel.isAuthenticated.value == null
            }

        }

        setContent {
            FinancialTrackerAppTheme {
                val isAuthenticated by globalViewModel.isAuthenticated.collectAsState()
                globalViewModel.loadUserData()
                MainScreen(isAuthenticated)
            }
        }
    }
}













