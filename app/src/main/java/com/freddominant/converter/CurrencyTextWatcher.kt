package com.freddominant.converter

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import com.freddominant.converter.models.Currency
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CurrencyTextWatcher(private val currencyAdapter: CurrencyAdapter) : TextWatcher {

    @SuppressLint("CheckResult")
    override fun afterTextChanged(s: Editable?) {
        this.currencyAdapter.toggleShouldUpdate(false)
        Observable.just(s.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .debounce(2000, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribe({
                if (!it.isNullOrBlank()) {
//                    this.currencyAdapter.setAmount(it.toString().toDouble())
                } else {
//                    this.currencyAdapter.setAmount(0.0)
                }
            }, { })
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
}