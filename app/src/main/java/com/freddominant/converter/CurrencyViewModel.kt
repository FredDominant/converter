package com.freddominant.converter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.freddominant.converter.models.Currency
import com.freddominant.converter.network.CurrencyAPI
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class CurrencyViewModel: ViewModel() {

    var compositeDisposable = CompositeDisposable()

    var currencies = MutableLiveData<ArrayList<Currency>>()

    var hasError = MutableLiveData<Boolean>()

    init {
        this.startSubscription()
    }

    fun startSubscription(currencyCode: String = "EUR") {
        compositeDisposable.add(Observable.interval(1000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                this.fetchCurrencies(currencyCode)
            }, { this.handleError(it) })
        )
    }

    fun getCurrencies() : LiveData<ArrayList<Currency>> {
        return this.currencies
    }

    fun fetchCurrencies(currencyCode: String) {
        compositeDisposable.add(CurrencyAPI()
            .getCurrenciesFromAPI(currencyCode)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.getCurrencyRate().isNotEmpty()) this.currencies.value = it.getCurrencyRate()
            }, { this.handleError(it) })
        )
    }

    private fun handleError(error: Throwable) {
        error.let {
            this.hasError.value = true
        }
    }

    fun disposeDisposable() {
        this.compositeDisposable.dispose()
    }
}