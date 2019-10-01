package com.freddominant.converter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.freddominant.converter.models.Currency
import com.freddominant.converter.network.CurrencyTextWatcher
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_currency_row.view.*

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
        containerView.currencyInput.setText("${currency.value}")
        containerView.setOnClickListener {
            this.clickListener.onCurrencyItemClicked(currency)
            containerView.currencyInput.requestFocus()
            this.moveToTop()
        }
        containerView.currencyInput.addTextChangedListener(CurrencyTextWatcher(this.currencyAdapter, clickListener, currency))
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
