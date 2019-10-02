package com.freddominant.converter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.freddominant.converter.models.Currency
import kotlinx.android.synthetic.main.activity_main.*

class CurrencyActivity : AppCompatActivity(), OnCurrencyItemSelectedListener {

    private lateinit var viewModel: CurrencyViewModel
    private val threadLocal = ThreadLocal<CurrencyAdapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)
        shimmerLayout.startShimmerAnimation()

        this.setUpAdapter()
        this.viewModel = ViewModelProviders.of(this).get(CurrencyViewModel::class.java)
        this.registerForSubscription()
    }

    private fun setUpAdapter() {
        val currencyAdapter = CurrencyAdapter(this)
        this.threadLocal.set(currencyAdapter)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        currencyList.adapter = currencyAdapter
        currencyList.layoutManager = layoutManager
    }

    private fun registerForSubscription() {
        this.viewModel.getCurrencies().observe(this, Observer { currencies ->
            val currencyAdapter = this.threadLocal.get()
            currencyAdapter?.let { adapter ->
                shimmerLayout.also {
                    it.stopShimmerAnimation()
                    it.visibility = View.GONE
                }

                if (currencies.isNotEmpty()) {
                    currencyList.setItemViewCacheSize(currencies.size)
                    adapter.updateAdapter(currencies)
                }
            }
        })
    }

    override fun onCurrencyItemClicked(currency: Currency) {
        this.viewModel.disposeDisposable()
        this.viewModel.startSubscription(currency.currencyCode)
    }

    override fun scrollToTop() {
        currencyList.scrollToPosition(0)
    }

    override fun onStop() {
        super.onStop()
        this.viewModel.disposeDisposable()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.viewModel.disposeDisposable()
    }

}
