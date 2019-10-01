package com.freddominant.converter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.freddominant.converter.models.Currency

class CurrencyAdapter(private val clickListener: OnCurrencyItemSelectedListener): RecyclerView.Adapter<CurrencyViewHolder>() {

    private var shouldUpdate = true
    private var currencies = mutableListOf<Currency>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_currency_row, parent, false)
        return CurrencyViewHolder(view, this.clickListener, this)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bindValues(this.currencies[position])
    }

    override fun getItemCount() = this.currencies.size

    fun getCurrencies() = this.currencies

    fun updateAdapter(currencies: ArrayList<Currency>) {
        if (this.shouldUpdate) {
            this.currencies = currencies
            this.currencies.forEachIndexed { index, _ ->  this.notifyItemChanged(index) }
        }
    }

    fun toggleShouldUpdate(update: Boolean) {
        this.shouldUpdate = update
    }

}