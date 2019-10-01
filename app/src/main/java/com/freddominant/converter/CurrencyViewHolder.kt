package com.freddominant.converter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.freddominant.converter.models.Currency
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_currency_row.view.*

class CurrencyViewHolder(override val containerView: View, private val requestManager: RequestManager): RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun bindValues(currency: Currency) {
        containerView.currencyName.text = currency.getCurrencyName()
        containerView.currencyCode.text = currency.currencyCode
        containerView.currencyInput.setText("${currency.value}")
        containerView.currencyFlag.setImageDrawable(containerView.resources.getDrawable(currency.getFlag()))
    }

}
