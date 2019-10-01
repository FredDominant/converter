package com.freddominant.converter.network

import com.freddominant.converter.models.CurrencyResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("latest")
    fun getCurrencies(@Query("base") base: String) : Observable<CurrencyResponse>
}