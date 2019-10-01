package com.freddominant.converter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.freddominant.converter.models.Currency

class CurrencyAdapter(private val requestManager: RequestManager): RecyclerView.Adapter<CurrencyViewHolder>() {

    private var currencies = mutableListOf<Currency>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_currency_row, parent, false)
        return CurrencyViewHolder(view, this.requestManager)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bindValues(this.currencies[position])
    }

    override fun getItemCount() = this.currencies.size

    fun updateAdapter(currencies: ArrayList<Currency>) {
        this.currencies = currencies
        this.notifyDataSetChanged()
    }
}