package com.freddominant.converter.network

import com.freddominant.converter.models.CurrencyResponse
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class CurrencyAPI {

    var client: NetworkService

    init {
        this.client = NetworkFactory().getRetrofit().create(NetworkService::class.java)
    }

    fun getCurrenciesFromAPI(base: String = "EUR"): Observable<CurrencyResponse> {
        return this.client.getCurrencies(base)
            .subscribeOn(Schedulers.io())
    }
}