package com.example.financialtrackerapp.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financialtrackerapp.R
import com.example.financialtrackerapp.presentation.ui.theme.Black
import com.example.financialtrackerapp.presentation.ui.theme.DarkGrey
import com.example.financialtrackerapp.presentation.ui.theme.GradientSoft
import com.example.financialtrackerapp.presentation.ui.theme.UsernamePasswordTextField
import com.example.financialtrackerapp.presentation.ui.theme.poppinsFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsernameField(username: String, onValueChange: (String) -> Unit) {
    Column {
        Text(
            text = "Введите имя",
            fontSize = 14.sp,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium,
            color = Black,
            modifier = Modifier.padding(start = 25.dp, bottom = 4.dp)
        )

        OutlinedTextField(
            value = username,
            onValueChange = onValueChange,
            singleLine = true,
            placeholder = { Text(text = "Имя пользователя") },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = "User Icon",
                    tint = Black
                )
            },
            textStyle = TextStyle(
                fontFamily = poppinsFontFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = Black,
            ),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = UsernamePasswordTextField
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(horizontal = 25.dp)

        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPasswordField(
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: () -> Unit
) {
    Column {
        Text(
            text = "Пароль",
            fontSize = 14.sp,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium,
            color = Black,
            modifier = Modifier.padding(start = 26.dp, bottom = 4.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            placeholder = { Text(text = "Введите пароль") },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_password),
                    contentDescription = "Password Icon",
                    tint = Black
                )
            },
            trailingIcon = {
                IconButton(onClick = onPasswordVisibilityChange) {
                    Icon(
                        painter = painterResource(
                            id = if (passwordVisible) R.drawable.ic_password_make_visible else R.drawable.ic_password_make_invisible
                        ),
                        contentDescription = "Toggle password visibility"
                    )
                }
            },
            textStyle = TextStyle(
                fontFamily = poppinsFontFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = Black,
            ),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = UsernamePasswordTextField
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(horizontal = 25.dp)

        )

        Text(
            text = "Забыли пароль?",
            fontSize = 12.sp,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium,
            color = Black,
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 25.dp, top = 4.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationPasswordField(
    label: String,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: () -> Unit
) {
    Column {
        Text(
            text = label,
            fontSize = 14.sp,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium,
            color = Black,
            modifier = Modifier.padding(start = 26.dp, bottom = 4.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            placeholder = { Text(text = "Введите пароль") },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_password),
                    contentDescription = "Password Icon",
                    tint = Black
                )
            },
            trailingIcon = {
                IconButton(onClick = onPasswordVisibilityChange) {
                    Icon(
                        painter = painterResource(
                            id = if (passwordVisible) R.drawable.ic_password_make_visible else R.drawable.ic_password_make_invisible
                        ),
                        contentDescription = "Toggle password visibility"
                    )
                }
            },
            textStyle = TextStyle(
                fontFamily = poppinsFontFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = Black,
            ),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = UsernamePasswordTextField
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(horizontal = 25.dp)

        )
    }
}

@Composable
fun UniversalAuthButton(textLabel: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues(),
    ) {
        Box(
            modifier = Modifier
                .background(GradientSoft)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .size(width = 314.dp, height = 40.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = textLabel,
                fontSize = 18.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                color = Black,
            )
        }
    }
}

@Composable
fun CreateAnAccountRow(onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.rectangle_3),
            contentDescription = "rectangle1",
            tint = DarkGrey
        )

        Text(
            text = "Создать аккаунт",
            fontSize = 13.sp,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium,
            color = Black,
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .clickable(enabled = true) { onClick() }
        )

        Icon(
            painter = painterResource(id = R.drawable.rectangle_4),
            contentDescription = "rectangle2",
            tint = DarkGrey
        )
    }
}

