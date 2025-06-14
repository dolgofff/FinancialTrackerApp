package com.example.financialtrackerapp.app.platform

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.example.financialtrackerapp.domain.model.enums.Category
import com.example.financialtrackerapp.domain.model.enums.TransactionType
import com.example.financialtrackerapp.domain.usecase.account.GetCurrentAccountUseCase
import com.example.financialtrackerapp.domain.usecase.budgets.UpdateSpentValueUseCase
import com.example.financialtrackerapp.domain.usecase.transactions.CreateTransactionUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SideNotificationListenerService : NotificationListenerService() {

    @Inject
    lateinit var getCurrentAccountUseCase: GetCurrentAccountUseCase

    @Inject
    lateinit var createTransactionUseCase: CreateTransactionUseCase

    @Inject
    lateinit var updateSpentValueUseCase: UpdateSpentValueUseCase

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        val bankPackages = listOf("ru.sberbankmobile", "ru.sberbank.sberbankid")
        sbn?.let {
            val packageName = it.packageName
            if (packageName in bankPackages) {
                val extras = it.notification.extras
                //val title = extras.getString(Notification.EXTRA_TITLE)
                val text = extras.getCharSequence(Notification.EXTRA_TEXT)?.toString()

                if (!text.isNullOrBlank()) {
                    CoroutineScope(Dispatchers.Default).launch {
                        processNotification(text)
                    }
                }
            }
        }
    }

    private suspend fun processNotification(message: String) {
        val amountRegex = Regex("""(\d+[\.,]?\d*)\s*₽""")
        val match = amountRegex.find(message)

        val amount = match?.groups?.get(1)?.value?.replace(",", ".")?.toDoubleOrNull()
        val category = guessCategory(message)

        if (amount != null) {
            val currentAccount = getCurrentAccountUseCase()
            currentAccount?.let {
                createTransactionUseCase(
                    accountId = it.id,
                    amount = amount,
                    category = category,
                    type = guessType(message),
                    date = System.currentTimeMillis(),
                    note = "Automatically generated transaction.",
                    place = guessPlace(message),
                )

                updateSpentValueUseCase(
                    category = category,
                    amount = amount.toLong()
                )
            }
        }
    }

    private fun guessPlace(message: String): String? {
        val lower = message.lowercase()

        val knownPlaces = listOf(
            "пятёрочка", "магнит", "дикси", "перекресток", "лента", "азбука вкуса",
            "яндекс", "spotify", "netflix", "youtube", "sbermarket", "wildberries",
            "ozon", "вкусвилл", "мвидео", "dns", "авито", "теремок", "вкусно и точка"
        )

        val found = knownPlaces.firstOrNull { it in lower }
        return found?.replaceFirstChar { it.uppercase() }
    }

    private fun guessType(message: String): TransactionType {
        val lower = message.lowercase()

        return when {
            listOf(
                "зачисление",
                "зарплата",
                "получен перевод",
                "поступление",
                "перевод от",
                "входящий перевод"
            ).any { it in lower } ->
                TransactionType.INCOME

            listOf(
                "перевод",
                "списание на",
                "отправлен перевод",
                "переведено",
                "между своими счетами"
            ).any { it in lower } ->
                TransactionType.TRANSFER

            listOf(
                "покупка",
                "оплата",
                "списание",
                "снятие",
                "списано",
                "расход",
                "успешная оплата"
            ).any { it in lower } ->
                TransactionType.EXPENSE

            else -> TransactionType.EXPENSE
        }
    }

    private fun guessCategory(message: String): Category {
        val lower = message.lowercase()

        return when {
            listOf(
                "пятёрочка",
                "магнит",
                "дикси",
                "перекресток",
                "продукты",
                "супермаркет",
                "азбука вкуса",
                "лента",
                "вкусвилл"
            ).any { it in lower } ->
                Category.PRODUCTS

            listOf(
                "жкх",
                "коммунальные",
                "свет",
                "вода",
                "газ",
                "электроэнергия",
                "оплата услуг"
            ).any { it in lower } ->
                Category.HOUSEHOLD_GOODS

            listOf(
                "аренда",
                "квартира",
                "жилье"
            ).any { it in lower } ->
                Category.PROPERTY_RENT

            listOf(
                "вкусно и точка",
                "теремок",
                "манеки",
                "kfc",
                "ресторан",
                "кафе",
                "бургер",
                "еда вне дома"
            ).any { it in lower } ->
                Category.EATING_OUTS

            listOf(
                "метро",
                "троллейбус",
                "автобус",
                "маршрутка",
                "транспорт",
                "оплата проезда"
            ).any { it in lower } ->
                Category.PUBLIC_TRANSPORT

            listOf(
                "подарок"
            ).any { it in lower } ->
                Category.PRESENTS

            listOf(
                "яндекс.такси",
                "яндекс go",
                "uber",
                "поездка на такси",
                "такси"
            ).any { it in lower } ->
                Category.TAXI

            listOf(
                "подписка",
                "spotify",
                "youtube premium",
                "netflix",
                "музыка",
                "ivi"
            ).any { it in lower } ->
                Category.SUBSCRIPTIONS

            listOf(
                "косметика",
                "уход",
                "крем",
                "салон красоты",
                "парикмахер",
                "маникюр"
            ).any { it in lower } ->
                Category.BEAUTY_GOODS

            listOf(
                "одежда",
                "обувь"
            ).any { it in lower } ->
                Category.CLOTHES

            listOf(
                "путешествие",
                "авиабилет",
                "бронирование",
                "отель",
                "поездка",
                "booking",
                "travel"
            ).any { it in lower } ->
                Category.TRAVELINGS

            listOf(
                "зоомагазин",
                "ветеринар",
                "зоотовары"
            ).any { it in lower } ->
                Category.PETS

            listOf(
                "штраф",
                "пдд",
                "гибдд",
                "оплата штрафа"
            ).any { it in lower } ->
                Category.FINES

            listOf(
                "налог",
                "фнс",
                "налоговая",
                "госуслуги"
            ).any { it in lower } ->
                Category.TAXES

            listOf(
                "пожертвование",
                "благотворительность",
                "фонд"
            ).any { it in lower } ->
                Category.CHARITY

            listOf(
                "разное",
                "прочее",
                "другое"
            ).any { it in lower } ->
                Category.RANDOM_SPENDING

            listOf(
                "мтс",
                "билайн",
                "мегафон",
                "tele2",
                "интернет",
                "связь",
                "пополнение счета"
            ).any { it in lower } ->
                Category.COMMUNICATION_SERVICES

            listOf(
                "аптека",
                "медицина",
            ).any { it in lower } ->
                Category.MEDICINE

            listOf(
                "кино",
                "театр",
                "развлечения",
                "парк",
                "аттракционы"
            ).any { it in lower } ->
                Category.ENTERTAINMENTS

            listOf(
                "смартфон",
                "техника",
                "устройство",
                "гаджет",
                "телефон",
                "ноутбук"
            ).any { it in lower } ->
                Category.DEVICES

            listOf(
                "зарплата",
                "зачисление"
            ).any { it in lower } ->
                Category.SALARY

            listOf(
                "дивиденды",
                "доход от инвестиций",
                "инвестиции",
                "брокер"
            ).any { it in lower } ->
                Category.INVESTMENTS_INCOME

            listOf(
                "услуги",
                "выплата по заказу"
            ).any { it in lower } ->
                Category.PART_TIME_JOBS

            else -> Category.EMPTY_CATEGORY
        }
    }
}