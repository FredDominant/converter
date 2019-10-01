package com.freddominant.converter

import com.freddominant.converter.models.Currency

interface OnCurrencyItemSelectedListener {
    fun onCurrencyItemClicked(currency: Currency)
    fun scrollToTop()
}