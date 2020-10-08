package com.kanawish.recordstore.tools

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

/**
 * If memory serves, currency formatting is possibly a resource intensive
 * thing to instantiate every time.
 *
 * If that assumption is correct, it could be somewhat safeish to 'memorize'
 * an instance of it. (I suspect it's stateful enough to be dangerous,
 * so keeping an eye.)
 */
class PriceFormatter() {
    private val defaultLocale: Locale = Locale.getDefault()
    private val currency: Currency = Currency.getInstance(defaultLocale)
    private val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance(defaultLocale)

    fun format(price: Long): String {
        return currencyFormat.format(BigDecimal(price).movePointLeft(currency.defaultFractionDigits))
    }
}

/**
 * If it's cheap, this works. It's probably expensive tho.
 */
fun Long.currencyFormat(): String {
    val defaultLocale = Locale.getDefault()
    val currency = Currency.getInstance(defaultLocale)
    val currencyFormat = NumberFormat.getCurrencyInstance(defaultLocale)

    return currencyFormat.format(BigDecimal(this).movePointLeft(currency.defaultFractionDigits))
}