package br.com.sympla.conversormoedas.exchange_rates.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.sympla.conversormoedas.R
import br.com.sympla.conversormoedas.model.CurrencyListItemModel
import br.com.sympla.conversormoedas.model.ExchangeRateModel

class RateCurrencyListAdapter :
    RecyclerView.Adapter<RateCurrencyListViewHolder>() {

    private var currencyList: List<CurrencyListItemModel> = emptyList()
    private lateinit var exchangeRateModel: ExchangeRateModel
    private  var rate:Float = 0.0f

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateCurrencyListViewHolder =
        RateCurrencyListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_exchange_rate ,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = currencyList.size

    override fun onBindViewHolder(holder: RateCurrencyListViewHolder, position: Int) {
        val item = currencyList[position]
        holder.bind(item, exchangeRateModel, rate)
    }

    fun loadRateCurrencyList(currencyList: List<CurrencyListItemModel>, exchangeRateModel: ExchangeRateModel, rate:Float) {
        this.currencyList = currencyList
        this.exchangeRateModel = exchangeRateModel
        this.rate = rate
        notifyDataSetChanged()
    }
}