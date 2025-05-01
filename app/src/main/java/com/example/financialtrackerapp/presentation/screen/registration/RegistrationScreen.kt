package com.example.financialtrackerapp.presentation.screen.registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.financialtrackerapp.R
import com.example.financialtrackerapp.presentation.navigation.destinations.LoginScreenObject
import com.example.financialtrackerapp.presentation.navigation.destinations.RegistrationScreenObject
import com.example.financialtrackerapp.presentation.ui.components.RegistrationPasswordField
import com.example.financialtrackerapp.presentation.ui.components.UniversalAuthButton
import com.example.financialtrackerapp.presentation.ui.components.UsernameField
import com.example.financialtrackerapp.presentation.ui.theme.GradientSoft
import com.example.financialtrackerapp.presentation.ui.theme.MainBackground
import com.example.financialtrackerapp.presentation.ui.theme.SecondaryBackground
import com.example.financialtrackerapp.presentation.ui.theme.SpecificOrange
import com.example.financialtrackerapp.presentation.ui.theme.poppinsFontFamily

@Composable
fun RegistrationScreen(
    navController: NavController,
    registrationViewModel: RegistrationViewModel = hiltViewModel()
) {
    val registrationState = registrationViewModel.registrationState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { snackbarData ->
                Snackbar(
                    containerColor = MainBackground,
                    shape = RoundedCornerShape(16.dp),
                    contentColor = SpecificOrange
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = snackbarData.visuals.message,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MainBackground)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.img_bank),
                    contentDescription = "Bank Logo",
                    modifier = Modifier
                        .padding(top = 40.dp, end = 10.dp)
                        .size(width = 180.dp, height = 186.dp)
                        .align(alignment = Alignment.TopCenter)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.70f)
                        .align(Alignment.BottomCenter)
                        .clip(RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp))
                        .background(SecondaryBackground),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                )
                {
                    Text(
                        text = "REGISTRATION",
                        style = TextStyle(brush = GradientSoft),
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 36.sp,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Spacer(modifier = Modifier.size(40.dp))

                    UsernameField(username = registrationState.value.username) {
                        registrationViewModel.updateUsernameText(it)
                    }

                    Spacer(modifier = Modifier.size(48.dp))

                    RegistrationPasswordField(
                        label = "Password",
                        password = registrationState.value.password,
                        onPasswordChange = { registrationViewModel.updatePasswordText(it) },
                        passwordVisible = registrationViewModel.passwordVisible.collectAsState().value,
                        onPasswordVisibilityChange = { registrationViewModel.togglePasswordVisibility() }
                    )

                    Spacer(modifier = Modifier.size(48.dp))

                    RegistrationPasswordField(
                        label = "Confirm password",
                        password = registrationState.value.confirmPassword,
                        onPasswordChange = { registrationViewModel.updateConfirmPasswordText(it) },
                        passwordVisible = registrationViewModel.passwordVisible.collectAsState().value,
                        onPasswordVisibilityChange = { registrationViewModel.togglePasswordVisibility() }
                    )

                    Spacer(modifier = Modifier.size(64.dp))

                    UniversalAuthButton(
                        textLabel = "Register",
                        onClick = { registrationViewModel.register() }
                    )

                    LaunchedEffect(
                        registrationState.value.isRegistered,
                        registrationState.value.errorMessage
                    ) {
                        if (registrationState.value.isRegistered) {
                            snackbarHostState.showSnackbar("Successfully registered!")
                            navController.navigate(LoginScreenObject) {
                                popUpTo(RegistrationScreenObject) { inclusive = true }
                            }
                        } else {
                            registrationState.value.errorMessage?.let {
                                snackbarHostState.showSnackbar(it)
                            }
                        }
                    }

                }
            }
        })
}