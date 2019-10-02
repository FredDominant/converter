package com.freddominant.converter.models

import android.os.Build
import com.mynameismidori.currencypicker.ExtendedCurrency
import java.util.*
import java.util.Currency

data class Currency (val currencyCode: String, var value: Double) {
    var userAmount = 1.0

    private fun getCurrencyByCountryCode() = Currency.getInstance(this.currencyCode)

    fun getCurrencyName(): String {
        val currency = this.getCurrencyByCountryCode()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            currency.getDisplayName(Locale.getDefault())
        } else {
            currency.toString()
        }
    }

    fun getFlag() : Int {
        val currency = ExtendedCurrency.getCurrencyByISO(this.getCurrencyByCountryCode().currencyCode)
        return currency?.let { it.flag } ?: -1
    }
}