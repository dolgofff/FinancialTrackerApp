package com.example.financialtrackerapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
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
                //ChangeSystemBarsTheme(!isSystemInDarkTheme())
                val isAuthenticated by globalViewModel.isAuthenticated.collectAsState()

                LaunchedEffect(isAuthenticated) {
                    if (isAuthenticated == true) {
                        globalViewModel.loadUserData()
                    }
                }

                isAuthenticated?.let {
                    MainScreen(
                        isAuthenticated = it,
                        globalViewModel = globalViewModel
                    )
                }
            }
        }
    }

    /* @Composable
     private fun ChangeSystemBarsTheme(lightTheme: Boolean) {
         val barColor = MaterialTheme.colorScheme.background.toArgb()
         LaunchedEffect(lightTheme) {
             if (lightTheme) {
                 enableEdgeToEdge(
                     statusBarStyle = SystemBarStyle.light(
                         barColor, barColor,
                     ),
                     navigationBarStyle = SystemBarStyle.light(
                         barColor, barColor,
                     ),
                 )
             } else {
                 enableEdgeToEdge(
                     statusBarStyle = SystemBarStyle.dark(
                         barColor,
                     ),
                     navigationBarStyle = SystemBarStyle.dark(
                         barColor,
                     ),
                 )
             }
         }
     }*/
}














