package com.freddominant.converter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.freddominant.converter.models.Currency
import com.freddominant.converter.network.CurrencyAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class CurrencyViewModel: ViewModel() {

    private var disposable: Disposable? = null

    private val currencies: MutableLiveData<ArrayList<Currency>> by lazy {
        MutableLiveData<ArrayList<Currency>>().also {
            fetchCurrencies()
        }
    }

    fun getCurrencies() : LiveData<ArrayList<Currency>> {
        return this.currencies
    }

    private fun fetchCurrencies() {
        this.disposable = CurrencyAPI()
            .getCurrenciesFromAPI()
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                it.getCurrencyRate().map { rate -> Log.e(">>>>", "Currency is ${rate.getCurrencyName()} and Flag is: ${rate.getFlag()}") }
                if (it.getCurrencyRate().isNotEmpty()) this.currencies.value = it.getCurrencyRate()
            }, { error -> this.handleError(error) })
    }

    fun handleError(error: Throwable) {
        error.let {
            Log.e("Error is ", error.localizedMessage)
            error.printStackTrace()
        }

    }

    fun disposeDisposable() {
        this.disposable?.let {
            if (!it.isDisposed) it.dispose()
        }
    }
}