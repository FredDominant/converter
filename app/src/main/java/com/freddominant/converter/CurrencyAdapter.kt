package com.freddominant.converter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.freddominant.converter.models.Currency

class CurrencyAdapter(private val clickListener: OnCurrencyItemSelectedListener): RecyclerView.Adapter<CurrencyViewHolder>() {

    private var shouldUpdate = true
    private var currencies = ArrayList<Currency>()
    private var selectedItem : Currency? = null
    private var amount = 1.0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_currency_row, parent, false)
        return CurrencyViewHolder(view, this.clickListener, this)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bindValues(this.currencies[position])
    }

    override fun getItemCount() = this.currencies.size


    override fun getItemId(position: Int) = position.toLong()

    fun getCurrencies() = this.currencies

    fun updateAdapter(currencies: ArrayList<Currency>) {
        if (selectedItem != null) {
            currencies.filter {
                !it.currencyCode.equals(selectedItem!!.currencyCode, true)
            }.also {
                this.currencies = ArrayList(it)
                this.currencies.add(0, this.selectedItem!!)
                this.currencies.forEachIndexed { index, currency ->
                    if (index != 0) {
                        this.notifyItemChanged(index, currency)
                    }
                }
            }
        } else {
            this.currencies = currencies
            this.notifyDataSetChanged()
        }
    }

    fun setSelectedItem(currency: Currency) {
        this.selectedItem = currency
    }

    fun toggleShouldUpdate(update: Boolean) {
        this.shouldUpdate = update
    }

    fun setAmount(amount: Double) {
        this.amount = amount
    }

    fun getAmount() = this.amount

}