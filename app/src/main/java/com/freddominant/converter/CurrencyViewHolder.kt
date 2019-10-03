package com.freddominant.converter

import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.freddominant.converter.models.Currency
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_currency_row.view.*
import java.text.DecimalFormat

class CurrencyViewHolder(
    override val containerView: View,
    private val clickListener: OnCurrencyItemSelectedListener,
    private val currencyAdapter: CurrencyAdapter) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bindValues(currency: Currency) {

        this.containerView.also {
            it.currencyName.text = currency.getCurrencyName()
            it.currencyCode.text = currency.currencyCode
            it.currencyFlag.setImageDrawable(containerView.resources.getDrawable(currency.getFlag()))

            it.setOnClickListener { view -> this.handleContainerClick(view, currency) }

            val decimalFormat = DecimalFormat("#.###")
            val exchangedValue = currency.value * this.currencyAdapter.amount
            it.currencyInput.setText(decimalFormat.format(exchangedValue))
            it.currencyInput.setOnFocusChangeListener { editText, hasFocus ->
                this.handleInputTextFocusChange(editText, hasFocus, currency) }

//            it.currencyInput.setOnFocusChangeListener { view , hasFocus ->
//                if (hasFocus) {
//                    it.currencyInput
//                        .addTextChangedListener(CurrencyTextWatcher(this.currencyAdapter, currency))
//                }
//
//            }
        }
    }

    private fun handleInputTextFocusChange(view: View, hasFocus: Boolean, currency: Currency) {
        if (hasFocus) {
            (view as EditText?)?.let {
                it.addTextChangedListener(CurrencyTextWatcher(this.currencyAdapter, currency))
            }
        }
    }

    private fun handleContainerClick(view: View, currency: Currency) {
        this.moveToTop()
        this.currencyAdapter.setSelectedItem(currency)
        this.clickListener.onCurrencyItemClicked(currency)
        view.currencyInput.also {
            it.isEnabled = true
            it.requestFocus()
            currency.userAmount = it.text.toString().toDouble()
        }
    }


    private fun moveToTop() {
        layoutPosition.takeIf { it > 0 }?.also { currentPosition ->
            this.currencyAdapter.getCurrencies().removeAt(currentPosition)
                .also {
                    this.currencyAdapter.getCurrencies().add(0, it)
                }
            this.currencyAdapter.notifyItemMoved(currentPosition, 0)
            this.clickListener.scrollToTop()
        }
    }
}
