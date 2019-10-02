package com.freddominant.converter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.freddominant.converter.models.Currency
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_currency_row.view.*
import java.text.DecimalFormat

class CurrencyViewHolder(
    override val containerView: View,
    private val clickListener: OnCurrencyItemSelectedListener,
    private val currencyAdapter: CurrencyAdapter
) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bindValues(currency: Currency) {
        containerView.currencyName.text = currency.getCurrencyName()
        containerView.currencyCode.text = currency.currencyCode
        containerView.currencyFlag.setImageDrawable(containerView.resources.getDrawable(currency.getFlag()))

        val decimalFormat = DecimalFormat("####.####")
        val exchangedValue = currency.value * this.currencyAdapter.getAmount()

        containerView.currencyInput.setText(decimalFormat.format(exchangedValue))
        containerView.setOnClickListener {
            this.moveToTop()
            this.currencyAdapter.setSelectedItem(currency)
            this.clickListener.onCurrencyItemClicked(currency)
            this.containerView.currencyInput.isEnabled = true
            containerView.currencyInput.requestFocus()
            currency.userAmount = this.containerView.currencyInput.text.toString().toDouble()
        }
        containerView.currencyInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                containerView.currencyInput
                    .addTextChangedListener(CurrencyTextWatcher(this.currencyAdapter, currency))
            }
        }
    }


    private fun moveToTop() {
        layoutPosition.takeIf { it > 0 }
            ?.also { currentPosition ->
                this.currencyAdapter.getCurrencies().removeAt(currentPosition)
                    .also {
                        this.currencyAdapter.getCurrencies().add(0, it)
                    }
                this.currencyAdapter.notifyItemMoved(currentPosition, 0)
                this.clickListener.scrollToTop()
            }

    }
}
