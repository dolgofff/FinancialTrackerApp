package com.example.financialtrackerapp.presentation.ui.components

import androidx.compose.ui.graphics.Color
import com.example.financialtrackerapp.R
import com.example.financialtrackerapp.domain.model.Transaction
import com.example.financialtrackerapp.domain.model.enums.AccountType
import com.example.financialtrackerapp.domain.model.enums.Category
import com.example.financialtrackerapp.domain.model.enums.Currency
import com.example.financialtrackerapp.domain.model.enums.TransactionType
import com.example.financialtrackerapp.presentation.ui.theme.NegativeBalance
import com.example.financialtrackerapp.presentation.ui.theme.PositiveBalance
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatNumber(amount: Double): String {
    val formatter = DecimalFormat("#,##0.00")
    return formatter.format(amount)
}

fun parseFormattedNumber(formatted: String): Double {
    val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
        groupingSeparator = ','
        decimalSeparator = '.'
    }
    val formatter = DecimalFormat("#,##0.00", symbols)
    formatter.isParseBigDecimal = true //чтобы не потерять точность, если нужно
    return formatter.parse(formatted)?.toDouble() ?: 0.0
}

fun categoryToIcon(category: Category): Int {
    return when (category) {
        Category.HOUSEHOLD_GOODS -> R.drawable.ics_home
        Category.PROPERTY_RENT -> R.drawable.ics_rent
        Category.EATING_OUTS -> R.drawable.ics_food
        Category.PUBLIC_TRANSPORT -> R.drawable.ics_transport
        Category.PRESENTS -> R.drawable.ics_gift
        Category.TAXI -> R.drawable.ics_taxi
        Category.SUBSCRIPTIONS -> R.drawable.ics_subscription
        Category.BEAUTY_GOODS -> R.drawable.ics_beauty
        Category.CLOTHES -> R.drawable.ics_clothes
        Category.TRAVELINGS -> R.drawable.ics_travel
        Category.EMPTY_CATEGORY -> R.drawable.ics_no_category
        Category.PETS -> R.drawable.ics_pets
        Category.FINES -> R.drawable.ics_fines
        Category.TAXES -> R.drawable.ics_taxes
        Category.CHARITY -> R.drawable.ics_charity
        Category.RANDOM_SPENDING -> R.drawable.ics_random
        Category.COMMUNICATION_SERVICES -> R.drawable.ics_ethernet
        Category.MEDICINE -> R.drawable.ics_medicine
        Category.PRODUCTS -> R.drawable.ics_products
        Category.ENTERTAINMENTS -> R.drawable.ics_joy
        Category.DEVICES -> R.drawable.ics_devices
        Category.ALL_EXPENSES -> R.drawable.ics_everithing
        Category.SALARY -> R.drawable.ics_salary
        Category.INVESTMENTS_INCOME -> R.drawable.ics_invest
        Category.PART_TIME_JOBS -> R.drawable.ics_shabashka
        Category.GIFTED_INCOME -> R.drawable.ics_gifts
    }
}

fun categoryToTitle(category: Category): String {
    return when (category) {
        Category.HOUSEHOLD_GOODS -> "Household goods"
        Category.PROPERTY_RENT -> "Rent payment"
        Category.EATING_OUTS -> "Eating out"
        Category.PUBLIC_TRANSPORT -> "Public transport"
        Category.PRESENTS -> "Presents"
        Category.TAXI -> "Taxi"
        Category.SUBSCRIPTIONS -> "Subscriptions"
        Category.BEAUTY_GOODS -> "Personal care products"
        Category.CLOTHES -> "Clothes"
        Category.TRAVELINGS -> "Travel"
        Category.EMPTY_CATEGORY -> "No category"
        Category.PETS -> "Pets"
        Category.FINES -> "Fines"
        Category.TAXES -> "Taxes"
        Category.CHARITY -> "Charity"
        Category.RANDOM_SPENDING -> "Random spending"
        Category.COMMUNICATION_SERVICES -> "Communication services"
        Category.MEDICINE -> "Medicine"
        Category.PRODUCTS -> "Products"
        Category.ENTERTAINMENTS -> "Entertainments"
        Category.DEVICES -> "Electronic devices"
        Category.ALL_EXPENSES -> "All expenses"
        Category.SALARY -> "Salary"
        Category.INVESTMENTS_INCOME -> "Investments income"
        Category.PART_TIME_JOBS -> "Part time jobs"
        Category.GIFTED_INCOME -> "Gifted money"
    }
}

fun accountTypeToIcon(accountType: AccountType): Int {
    return when (accountType) {
        AccountType.SINGLE -> R.drawable.ic_single_type
        AccountType.SHARED -> R.drawable.ic_shared_type
    }
}

fun accountTypeToTitle(accountType: AccountType): String {
    return when (accountType) {
        AccountType.SINGLE -> "Single Account"
        AccountType.SHARED -> "Shared Account"
    }
}

fun transactionTypeToColor(transactionType: TransactionType): Color {
    return when (transactionType) {
        TransactionType.EXPENSE -> NegativeBalance
        TransactionType.TRANSFER -> NegativeBalance
        TransactionType.INCOME -> PositiveBalance
    }
}

fun currencyToTitle(currency: Currency): String {
    return when (currency) {
        Currency.USD -> "$ USD"
        Currency.RUB -> "Р РУБ"
    }
}

fun titleToCurrency(currency: String): Currency {
    return when (currency) {
        "$ USD" -> Currency.USD
        "Р РУБ" -> Currency.RUB
        else -> Currency.USD
    }
}

fun formatTransactionDate(timestamp: Long): String {
    val date = Date(timestamp)
    val dateFormat = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.ENGLISH)
    return dateFormat.format(date)
}