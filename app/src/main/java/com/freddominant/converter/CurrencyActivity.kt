package com.freddominant.converter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class CurrencyActivity : AppCompatActivity() {

    private lateinit var viewModel: CurrencyViewModel
    private val threadLocal = ThreadLocal<CurrencyAdapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.setUpAdapter()
        this.viewModel = ViewModelProviders.of(this).get(CurrencyViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCurrencies().observe(this, Observer { currencies ->
            val currencyAdapter = this.threadLocal.get()
            currencyAdapter?.let { adapter ->
                if (currencies.isNotEmpty()) {
                    adapter.updateAdapter(currencies)
                }
            }
        })
    }

    override fun onStop() {
        super.onStop()
        this.viewModel.disposeDisposable()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.viewModel.disposeDisposable()
    }

    private fun setUpAdapter() {
        val currencyAdapter = CurrencyAdapter(Glide.with(this))
        this.threadLocal.set(currencyAdapter)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        currencyList.adapter = currencyAdapter
        currencyList.layoutManager = layoutManager
    }

}
