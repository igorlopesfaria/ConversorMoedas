package br.com.sympla.conversormoedas.currency.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.sympla.conversormoedas.R
import br.com.sympla.conversormoedas.model.CurrencyListItemModel

class CurencyListAdapter(private val clickListener: (CurrencyListItemModel) -> Unit) :
    RecyclerView.Adapter<CurrencyListViewHolder>() {

    private var currencyList: List<CurrencyListItemModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyListViewHolder =
        CurrencyListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_currency,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = currencyList.size

    override fun onBindViewHolder(holder: CurrencyListViewHolder, position: Int) {
        val item = currencyList[position]
        holder.itemView.setOnClickListener { clickListener(item) }
        holder.bind(item)
    }

    fun loadCurencyList(currencyList: List<CurrencyListItemModel>) {
        this.currencyList = currencyList
    }
}