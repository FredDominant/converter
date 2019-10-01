package com.freddominant.converter.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken


data class CurrencyResponse(
    @SerializedName("base")
    var base: String,

    @SerializedName("date")
    var date: String,

    @SerializedName("rates")
    var rates: CurrencyRate
) {
    fun getCurrencyRate() : ArrayList<Currency> {
        val currencies = ArrayList<Currency>()
        val jsonString = Gson().toJson(this.rates)
        val type = object : TypeToken<Map<String, Double>>() {}.type
        val map = Gson().fromJson<Map<String, Double>>(jsonString, type)
        map.forEach { (k, v) ->
            val currency = Currency(k, v)
            currencies.add(currency)
        }
        return currencies
    }

}