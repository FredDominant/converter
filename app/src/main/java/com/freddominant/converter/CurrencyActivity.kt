package com.freddominant.converter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.freddominant.converter.adapter.CurrencyAdapter
import com.freddominant.converter.models.Currency
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class CurrencyActivity : AppCompatActivity(), OnCurrencyItemSelectedListener {

    private lateinit var viewModel: CurrencyViewModel
    private val threadLocal = ThreadLocal<CurrencyAdapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.also {
            it.setContentView(R.layout.activity_main)
            shimmerLayout.startShimmerAnimation()
            it.setUpAdapter()
            it.viewModel = ViewModelProviders.of(it).get(CurrencyViewModel::class.java)
            it.registerForSubscription()
            it.observeNetworkError()
        }

    }


    private fun setUpAdapter() {
        val currencyAdapter = CurrencyAdapter(this)
        this.threadLocal.set(currencyAdapter)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        currencyList.also {
            it.adapter = currencyAdapter
            it.layoutManager = layoutManager
        }
    }

    private fun observeNetworkError() {
        this.viewModel.hasError.observe(this, Observer { hasError ->
            if (hasError) {
                shimmerLayout.stopShimmerAnimation()
                shimmerLayout.visibility = View.GONE
                Snackbar.make(parentContainer, "Please check your connection", Snackbar.LENGTH_LONG).also {
                    it.setAction("Retry") { this.registerForSubscription() }
                    it.show()
                }
            }
        })
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
        this.viewModel.also {
            it.disposeDisposable()
            it.startSubscription(currency.currencyCode)
        }
    }

    override fun scrollToTop() {
        currencyList.scrollToPosition(0)
    }


    override fun onDestroy() {
        super.onDestroy()
        this.viewModel.disposeDisposable()
    }

}
