package com.example.financialtrackerapp.presentation.ui.components

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financialtrackerapp.R
import com.example.financialtrackerapp.domain.model.enums.AccountType
import com.example.financialtrackerapp.domain.model.enums.Category
import com.example.financialtrackerapp.domain.model.enums.Currency
import com.example.financialtrackerapp.domain.model.enums.TransactionType
import com.example.financialtrackerapp.presentation.screen.dialogues.transaction.NewTransactionViewModel
import com.example.financialtrackerapp.presentation.ui.theme.Black
import com.example.financialtrackerapp.presentation.ui.theme.LightGrey
import com.example.financialtrackerapp.presentation.ui.theme.SecondaryBackground
import com.example.financialtrackerapp.presentation.ui.theme.SpecificOrange
import com.example.financialtrackerapp.presentation.ui.theme.White
import com.example.financialtrackerapp.presentation.ui.theme.poppinsFontFamily
import java.util.Calendar

@Composable
fun TripleChoiceButton(selectedType: TransactionType, onTypeSelected: (TransactionType) -> Unit) {
    Card(
        modifier = Modifier
            .size(width = 231.dp, height = 35.dp),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = SecondaryBackground),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ButtonTemplate(
                textLabel = "Доход",
                isSelected = selectedType == TransactionType.INCOME,
                onClick = { onTypeSelected(TransactionType.INCOME) }
            )
            ButtonTemplate(
                textLabel = "Трата",
                isSelected = selectedType == TransactionType.EXPENSE,
                onClick = { onTypeSelected(TransactionType.EXPENSE) }
            )
            ButtonTemplate(
                textLabel = "Перевод",
                isSelected = selectedType == TransactionType.TRANSFER,
                onClick = { onTypeSelected(TransactionType.TRANSFER) }
            )
        }
    }
}

@Composable
fun ButtonTemplate(
    textLabel: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(22.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) SpecificOrange else Color.Transparent
        ),
        contentPadding = PaddingValues(),
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = textLabel,
                fontSize = 11.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                color = Black,
            )
        }
    }
}

@Composable
fun ExpenseParameters(
    state: NewTransactionViewModel.TransactionState,
    currency: Currency,
    onAmountChange: (String) -> Unit,
    onCategoryChange: (Category) -> Unit,
    onNoteChange: (String) -> Unit,
    onPlaceChange: (String) -> Unit,
    onDateChange: (String) -> Unit,
    onSubmit: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(top = 50.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MoneyInputField(amount = state.amount, currency = currency, onAmountChange = onAmountChange)
        CategoryDropdown(selectedCategory = state.category, onCategoryChange = onCategoryChange)
        NoteInputField(note = state.note, onNoteChange = onNoteChange)
        DateInputField(date = state.date, label = "Дата", onDateChange)
        PlaceInputField(place = state.place, onPlaceChange = onPlaceChange)
        Spacer(modifier = Modifier.weight(1f))
        SubmissionButton(title = "Сохранить", onClick = onSubmit)
        Spacer(modifier = Modifier.size(32.dp))
    }
}

@Composable
fun IncomeParameters(
    state: NewTransactionViewModel.TransactionState,
    currency: Currency,
    onAmountChange: (String) -> Unit,
    onCategoryChange: (Category) -> Unit,
    onNoteChange: (String) -> Unit,
    onDateChange: (String) -> Unit,
    onSubmit: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(top = 50.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MoneyInputField(amount = state.amount, currency = currency, onAmountChange = onAmountChange)
        CategoryDropdown(selectedCategory = state.category, onCategoryChange = onCategoryChange)
        NoteInputField(note = state.note, onNoteChange = onNoteChange)
        DateInputField(date = state.date, label = "Дата", onDateChange)
        Spacer(modifier = Modifier.weight(1f))
        SubmissionButton("Сохранить", onClick = onSubmit)
        Spacer(modifier = Modifier.size(32.dp))
    }
}

@Composable
fun TransferParameters(
    state: NewTransactionViewModel.TransactionState,
    currency: Currency,
    onAmountChange: (String) -> Unit,
    onNoteChange: (String) -> Unit,
    onDateChange: (String) -> Unit,
    onSubmit: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(top = 50.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MoneyInputField(amount = state.amount, currency = currency, onAmountChange = onAmountChange)
        NoteInputField(note = state.note, onNoteChange = onNoteChange)
        DateInputField(date = state.date, label = "Дата", onDateChange)
        Spacer(modifier = Modifier.weight(1f))
        SubmissionButton("Сохранить", onClick = onSubmit)
        Spacer(modifier = Modifier.size(32.dp))
    }
}

@Composable
fun MoneyInputField(amount: Double, currency: Currency, onAmountChange: (String) -> Unit) {
    OutlinedTextField(
        value = formatNumber(amount),
        textStyle = TextStyle(
            fontSize = 40.sp,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = White,
            textAlign = TextAlign.End
        ),
        onValueChange = onAmountChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        suffix = {
            Text(
                text = currencyToSymbol(currency),
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 40.sp,
                color = White
            )
        },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            errorBorderColor = Color.Transparent,
        ),
        modifier = Modifier.fillMaxWidth()
    )

    Divisor()
}

@Composable
fun NoteInputField(note: String, onNoteChange: (String) -> Unit) {
    OutlinedTextField(
        value = note,
        textStyle = TextStyle(
            fontSize = 20.sp,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = White,
            textAlign = TextAlign.Start
        ),
        prefix = {
            Text(
                text = "Заметка",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                color = White,
                modifier = Modifier.padding(end = 5.dp)
            )
        },
        onValueChange = onNoteChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            errorBorderColor = Color.Transparent,
        ),
        modifier = Modifier.fillMaxWidth()
    )

    Divisor()
}

@Composable
fun DateInputField(date: String, label: String, onDateSelected: (String) -> Unit) {
    val showDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }

    if (showDialog.value) {
        DatePickerDialog(
            context,
            R.style.CustomDatePickerDialogTheme,
            { _, year, month, day ->
                calendar.set(year, month, day)
                onDateSelected(formatTransactionDate(calendar.timeInMillis))
                showDialog.value = false
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    OutlinedTextField(
        value = date,
        onValueChange = {},
        enabled = false,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDialog.value = true },
        textStyle = TextStyle(
            fontSize = 18.sp,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = SpecificOrange,
            textAlign = TextAlign.End
        ),
        prefix = {
            Text(
                text = label,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                color = White
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            errorBorderColor = Color.Transparent,
        )
    )

    Divisor()
}

@Composable
fun PlaceInputField(place: String, onPlaceChange: (String) -> Unit) {
    OutlinedTextField(
        value = place,
        textStyle = TextStyle(
            fontSize = 20.sp,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = White,
            textAlign = TextAlign.Start
        ),
        onValueChange = onPlaceChange,
        prefix = {
            Text(
                text = "Место",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                color = White,
                modifier = Modifier.padding(end = 5.dp)
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            errorBorderColor = Color.Transparent,
        ),
        modifier = Modifier.fillMaxWidth()
    )

    Divisor()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(selectedCategory: Category, onCategoryChange: (Category) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    val categories = listOf(
        Category.HOUSEHOLD_GOODS,
        Category.PROPERTY_RENT,
        Category.EATING_OUTS,
        Category.PUBLIC_TRANSPORT,
        Category.PRESENTS,
        Category.TAXI,
        Category.SUBSCRIPTIONS,
        Category.BEAUTY_GOODS,
        Category.CLOTHES,
        Category.TRAVELINGS,
        Category.EMPTY_CATEGORY,
        Category.PETS,
        Category.FINES,
        Category.TAXES,
        Category.CHARITY,
        Category.RANDOM_SPENDING,
        Category.COMMUNICATION_SERVICES,
        Category.MEDICINE,
        Category.PRODUCTS,
        Category.ENTERTAINMENTS,
        Category.DEVICES,
        Category.ALL_EXPENSES,
        Category.SALARY,
        Category.INVESTMENTS_INCOME,
        Category.PART_TIME_JOBS,
        Category.GIFTED_INCOME
    )

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = "", /*categoryToTitle(selectedCategory)*/
            onValueChange = {},
            readOnly = true,
            textStyle = TextStyle(
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                color = Color.White
            ),
            trailingIcon = {
                Icon(
                    modifier = Modifier.padding(end = 12.dp),
                    painter = painterResource(id = categoryToIcon(selectedCategory)),
                    contentDescription = "Category Icon",
                    tint = Color.Unspecified,
                )
            },
            prefix = {
                Text(
                    text = "Категория",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 22.sp,
                    color = White
                )
            },
            modifier = Modifier
                .menuAnchor() // Связывает меню с полем
                .fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
            ),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = categoryToIcon(category)),
                                contentDescription = "choice icon",
                                modifier = Modifier.size(31.dp),
                                tint = Color.Unspecified
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = categoryToTitle(category))
                        }
                    },
                    onClick = {
                        onCategoryChange(category)
                        expanded = false
                    }
                )
            }
        }
    }

    Divisor()
}

@Composable
fun SubmissionButton(title: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues(),
    ) {
        Box(
            modifier = Modifier
                .background(SpecificOrange)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .size(width = 199.dp, height = 48.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = Black,
            )
        }
    }
}

@Composable
fun Divisor() {
    Icon(
        painter = painterResource(id = R.drawable.grey_line),
        contentDescription = "divisor",
        tint = LightGrey,
    )
}

/* Account Part */

@Composable
fun InitialBalanceInputField(balance: Double, onBalanceChange: (String) -> Unit) {
    Divisor()
    OutlinedTextField(
        value = formatNumber(balance),
        textStyle = TextStyle(
            fontSize = 20.sp,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = White,
            textAlign = TextAlign.End,
        ),
        onValueChange = onBalanceChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        prefix = {
            Text(
                text = "Начальный баланс",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = White
            )
        },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            errorBorderColor = Color.Transparent,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 58.dp)
    )

    Divisor()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyDropdown(
    selectedCurrency: Currency,
    onCurrencyChange: (Currency) -> Unit
) {
    Divisor()

    var expanded by remember { mutableStateOf(false) }

    val currencies = listOf(Currency.USD, Currency.RUB)
    val labels = mapOf(
        Currency.USD to "$ USD",
        Currency.RUB to "₽ РУБ"
    )

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = labels[selectedCurrency] ?: "",
            onValueChange = {},
            readOnly = true,
            textStyle = TextStyle(
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = White,
                textAlign = TextAlign.End
            ),
            prefix = {
                Text(
                    text = "Валюта",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = White
                )
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .height(58.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,
            ),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            currencies.forEach { currency ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = labels[currency] ?: currency.name,
                            fontFamily = poppinsFontFamily,
                        )
                    },
                    onClick = {
                        onCurrencyChange(currency)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun NameInputField(name: String, onNameChange: (String) -> Unit) {
    Divisor()

    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        textStyle = TextStyle(
            fontSize = 20.sp,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = White,
            textAlign = TextAlign.Start
        ),
        prefix = {
            Text(
                text = "Название",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = White,
                modifier = Modifier.padding(end = 5.dp)
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            errorBorderColor = Color.Transparent,
        ),
        modifier = Modifier
            .fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountTypeDropdown(accountType: AccountType, onTypeChange: (AccountType) -> Unit) {
    Divisor()

    var expanded by remember { mutableStateOf(false) }

    val types = listOf(
        AccountType.SINGLE,
        AccountType.SHARED
    )

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            readOnly = true,
            textStyle = TextStyle(
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = White
            ),
            trailingIcon = {
                Icon(
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .size(31.dp),
                    painter = painterResource(id = accountTypeToIcon(accountType)),
                    contentDescription = "Account Type",
                    tint = Color.Unspecified,
                )
            },
            prefix = {
                Text(
                    text = "Тип счёта",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = White
                )
            },
            modifier = Modifier
                .menuAnchor() // Связывает меню с полем
                .fillMaxWidth()
                .height(height = 50.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
            ),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            types.forEach { type ->
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = accountTypeToIcon(type)),
                                contentDescription = "choice icon",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = accountTypeToTitle(type))
                        }
                    },
                    onClick = {
                        onTypeChange(type)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun SpecialAmountInputField(
    label: String,
    showDivisor: Boolean,
    amount: Double,
    onAmountChange: (String) -> Unit
) {
    Divisor()
    OutlinedTextField(
        value = formatNumber(amount),
        textStyle = TextStyle(
            fontSize = 20.sp,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = White,
            textAlign = TextAlign.End,
        ),
        onValueChange = onAmountChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        prefix = {
            Text(
                text = label,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = White
            )
        },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            errorBorderColor = Color.Transparent,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 58.dp)
    )

    if (showDivisor) {
        Divisor()
    }
}

/*@Composable
fun AmountInputField(label: String, amount: Double, onAmountChange: (String) -> Unit) {
    Divisor()
    OutlinedTextField(
        value = formatNumber(amount),
        textStyle = TextStyle(
            fontSize = 20.sp,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = White,
            textAlign = TextAlign.End,
        ),
        onValueChange = onAmountChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        prefix = {
            Text(
                text = label,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = White
            )
        },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            errorBorderColor = Color.Transparent,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 58.dp)
    )

    Divisor()
}*/