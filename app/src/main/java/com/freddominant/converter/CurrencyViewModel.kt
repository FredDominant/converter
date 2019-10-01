package com.freddominant.converter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.freddominant.converter.models.Currency
import com.freddominant.converter.network.CurrencyAPI
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class CurrencyViewModel: ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val currencies: MutableLiveData<ArrayList<Currency>> by lazy {
        MutableLiveData<ArrayList<Currency>>().also {
            this.startSubscription()
        }
    }

    private fun startSubscription() {
        compositeDisposable.add(Observable.interval(1000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                this.fetchCurrencies()
            }, { })
        )
    }

    fun getCurrencies() : LiveData<ArrayList<Currency>> {
        return this.currencies
    }

    private fun fetchCurrencies() {
        compositeDisposable.add(CurrencyAPI()
            .getCurrenciesFromAPI()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.getCurrencyRate().isNotEmpty()) this.currencies.value = it.getCurrencyRate()
            }, { error -> this.handleError(error) })
        )
    }

    fun handleError(error: Throwable) {
        error.let {
            Log.e("Error is ", error.localizedMessage)
            error.printStackTrace()
        }

    }

    fun disposeDisposable() {
        this.compositeDisposable.clear()
    }
}