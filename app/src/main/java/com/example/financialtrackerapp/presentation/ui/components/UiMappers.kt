package com.example.financialtrackerapp.presentation.ui.components

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.example.financialtrackerapp.R
import com.example.financialtrackerapp.domain.model.Advice
import com.example.financialtrackerapp.domain.model.enums.AccountType
import com.example.financialtrackerapp.domain.model.enums.Category
import com.example.financialtrackerapp.domain.model.enums.Currency
import com.example.financialtrackerapp.domain.model.enums.TransactionType
import com.example.financialtrackerapp.presentation.screen.advices.AdvicesViewModel
import com.example.financialtrackerapp.presentation.ui.theme.BeautyColor
import com.example.financialtrackerapp.presentation.ui.theme.CharityColor
import com.example.financialtrackerapp.presentation.ui.theme.ClothesColor
import com.example.financialtrackerapp.presentation.ui.theme.DevicesColor
import com.example.financialtrackerapp.presentation.ui.theme.EthernetColor
import com.example.financialtrackerapp.presentation.ui.theme.FinesColor
import com.example.financialtrackerapp.presentation.ui.theme.FoodColor
import com.example.financialtrackerapp.presentation.ui.theme.GiftColor
import com.example.financialtrackerapp.presentation.ui.theme.GiftsColor
import com.example.financialtrackerapp.presentation.ui.theme.HomeColor
import com.example.financialtrackerapp.presentation.ui.theme.InvestColor
import com.example.financialtrackerapp.presentation.ui.theme.JoyColor
import com.example.financialtrackerapp.presentation.ui.theme.MedicineColor
import com.example.financialtrackerapp.presentation.ui.theme.NegativeBalance
import com.example.financialtrackerapp.presentation.ui.theme.NoCategoryColor
import com.example.financialtrackerapp.presentation.ui.theme.PetsColor
import com.example.financialtrackerapp.presentation.ui.theme.PositiveBalance
import com.example.financialtrackerapp.presentation.ui.theme.ProductsColor
import com.example.financialtrackerapp.presentation.ui.theme.RandomColor
import com.example.financialtrackerapp.presentation.ui.theme.RentColor
import com.example.financialtrackerapp.presentation.ui.theme.SalaryColor
import com.example.financialtrackerapp.presentation.ui.theme.ShabashkaColor
import com.example.financialtrackerapp.presentation.ui.theme.SigmaColor
import com.example.financialtrackerapp.presentation.ui.theme.SubscriptionsColor
import com.example.financialtrackerapp.presentation.ui.theme.TaxesColor
import com.example.financialtrackerapp.presentation.ui.theme.TaxiColor
import com.example.financialtrackerapp.presentation.ui.theme.TransportColor
import com.example.financialtrackerapp.presentation.ui.theme.TravelColor
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatNumber(amount: Double): String {
    val formatter = DecimalFormat("#,##0.00")
    return formatter.format(amount)
}

fun parseFormattedNumber(formatted: String): Double {
    val symbols = DecimalFormatSymbols(Locale.US).apply {
        groupingSeparator = ','
        decimalSeparator = '.'
    }
    val formatter = DecimalFormat("#,##0.00", symbols)
    formatter.isParseBigDecimal = true

    val cleaned = formatted.replace("[^\\d,.-]".toRegex(), "")

    if (cleaned != formatted) {
        Log.w("parseFormattedNumber", "Strange input '$formatted' cleaned to '$cleaned'")
    }

    return try {
        formatter.parse(cleaned)?.toDouble() ?: 0.0
    } catch (e: ParseException) {
        0.0
    }
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
        Category.ALL_EXPENSES -> R.drawable.ics_everything
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

fun stringToCategory(name: String): Category {
    return Category.values().find {
        it.name.replace("_", " ")
            .lowercase()
            .replaceFirstChar { c -> c.uppercase() } == name
    } ?: Category.EMPTY_CATEGORY
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
        Currency.RUB -> "₽ РУБ"
    }
}

fun currencyToSymbol(currency: Currency): String {
    return when (currency) {
        Currency.USD -> "$"
        Currency.RUB -> "₽"
    }
}

fun titleToCurrency(currency: String): Currency {
    return when (currency) {
        "$ USD" -> Currency.USD
        "₽ РУБ" -> Currency.RUB
        else -> Currency.USD
    }
}

fun formatTransactionDate(timestamp: Long): String {
    val date = Date(timestamp)
    val dateFormat = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.ENGLISH)
    return dateFormat.format(date)
}

fun parseTransactionDate(dateString: String): Long {
    val dateFormat = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.ENGLISH)
    val date = dateFormat.parse(dateString)
    return date?.time ?: throw IllegalArgumentException("Invalid date format: $dateString")
}

val categoryColorMap = mapOf(
    Category.BEAUTY_GOODS to BeautyColor,
    Category.CHARITY to CharityColor,
    Category.CLOTHES to ClothesColor,
    Category.DEVICES to DevicesColor,
    Category.ENTERTAINMENTS to JoyColor,
    Category.FINES to FinesColor,
    Category.GIFTED_INCOME to GiftsColor,
    Category.HOUSEHOLD_GOODS to HomeColor,
    Category.INVESTMENTS_INCOME to InvestColor,
    Category.MEDICINE to MedicineColor,
    Category.PETS to PetsColor,
    Category.PRODUCTS to ProductsColor,
    Category.RANDOM_SPENDING to RandomColor,
    Category.PROPERTY_RENT to RentColor,
    Category.SALARY to SalaryColor,
    Category.PART_TIME_JOBS to ShabashkaColor,
    Category.SUBSCRIPTIONS to SubscriptionsColor,
    Category.TAXES to TaxesColor,
    Category.TAXI to TaxiColor,
    Category.PUBLIC_TRANSPORT to TransportColor,
    Category.TRAVELINGS to TravelColor,
    Category.EATING_OUTS to FoodColor,
    Category.COMMUNICATION_SERVICES to EthernetColor,
    Category.EMPTY_CATEGORY to NoCategoryColor,
    Category.ALL_EXPENSES to SigmaColor,
    Category.PRESENTS to GiftColor
)

fun adviceTypeToIcon(advice: AdvicesViewModel.Advice): Int {
    return when (advice.type) {
        AdvicesViewModel.AdviceType.CRITICAL -> R.drawable.rc_strong
        AdvicesViewModel.AdviceType.WARNING -> R.drawable.rc_middle
        AdvicesViewModel.AdviceType.POSITIVE -> R.drawable.rc_light
    }
}

fun indicateProgressColor(savedAmount: Double, targetAmount: Double): Color {
    val progress = (savedAmount / targetAmount).coerceIn(0.0, 1.0)

    return when {
        progress <= 0.2 -> ShabashkaColor
        progress <= 0.35 -> GiftsColor
        progress <= 0.55 -> FinesColor
        progress <= 0.75 -> SalaryColor
        else -> EthernetColor
    }
}

fun indicateBudgetProgressColor(spentAmount: Double, targetAmount: Double): Color {
    val progress = (spentAmount / targetAmount).coerceIn(0.0, 1.0)

    return when {
        progress <= 0.2 -> EthernetColor
        progress <= 0.35 -> SalaryColor
        progress <= 0.55 -> FinesColor
        progress <= 0.75 -> GiftsColor
        else -> ShabashkaColor
    }
}