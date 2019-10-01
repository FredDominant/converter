package com.freddominant.converter

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.freddominant.converter.network.CurrencyAPI
import io.reactivex.android.schedulers.AndroidSchedulers

class MainActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CurrencyAPI()
            .getCurrenciesFromAPI()
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                it.getCurrencyRate().map { rate -> Log.e("Currency is ", "$rate") }
            }, {
                Log.e("Error is ", it?.localizedMessage)
                it.printStackTrace()
            })
    }
}
