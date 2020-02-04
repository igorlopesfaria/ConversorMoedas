package br.com.sympla.conversormoedas.exchange_rates.adapter

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import br.com.sympla.conversormoedas.R
import br.com.sympla.conversormoedas.common.viewBind
import br.com.sympla.conversormoedas.model.CurrencyListItemModel
import br.com.sympla.conversormoedas.model.ExchangeRateModel
import com.github.siyamed.shapeimageview.CircularImageView
import com.squareup.picasso.Picasso
import java.text.NumberFormat


class RateCurrencyListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val flagIMG by viewBind<CircularImageView>(R.id.flag_image)
    private val titleTXT by viewBind<AppCompatTextView>(R.id.title)

    fun bind(currencyListItemModel: CurrencyListItemModel, exchangeRateModel: ExchangeRateModel, value:Float) {

        Picasso.get().load(currencyListItemModel.flagUrlPath)
        .into(flagIMG)

        var newValue:Float = 0.0f

        newValue =  if(exchangeRateModel.USD!= null && currencyListItemModel.shortName == "USD")
             value * exchangeRateModel.USD
        else if(exchangeRateModel.CAD!= null && currencyListItemModel.shortName == "CAD")
             value * exchangeRateModel.CAD
        else if(exchangeRateModel.EUR!= null && currencyListItemModel.shortName == "EUR")
             value * exchangeRateModel.EUR
        else if(exchangeRateModel.BRL!= null && currencyListItemModel.shortName == "BRL")
             value * exchangeRateModel.BRL
        else if(exchangeRateModel.GBP!= null && currencyListItemModel.shortName == "GBP")
             value * exchangeRateModel.GBP
        else
            value * 1.0f

        val newValueFormatted: String = NumberFormat.getCurrencyInstance().format(newValue).replace("R$","")

        titleTXT.text = "${currencyListItemModel.shortName} - ${currencyListItemModel.symbol} ${newValueFormatted}"

    }
}
