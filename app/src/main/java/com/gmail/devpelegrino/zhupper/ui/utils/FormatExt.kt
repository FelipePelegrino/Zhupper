package com.gmail.devpelegrino.zhupper.ui.utils

import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private const val FRIENDLY_DATE_PATTERN = "dd/MM/yyyy - HH:mm"

fun String.toFriendlyDate(): String {
    val outputFormatter = DateTimeFormatter.ofPattern(FRIENDLY_DATE_PATTERN)
    val dateTime = LocalDateTime.parse(this, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    return dateTime.format(outputFormatter)
}

fun Number.toCurrencyLocalNumberFormat(): String {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
    return currencyFormat.format(this)
}
