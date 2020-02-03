package br.com.sympla.conversormoedas.currency.adapter

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import br.com.sympla.conversormoedas.R
import br.com.sympla.conversormoedas.common.viewBind
import br.com.sympla.conversormoedas.model.CurrencyListItemModel
import com.github.siyamed.shapeimageview.CircularImageView
import com.squareup.picasso.Picasso

class CurrencyListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val flagIMG by viewBind<CircularImageView>(R.id.flag_image)
    private val titleTXT by viewBind<AppCompatTextView>(R.id.title)

    fun bind(currencyListItemModel: CurrencyListItemModel) {

        Picasso.get().load(currencyListItemModel.flagUrlPath)
        .into(flagIMG)

        titleTXT.text = currencyListItemModel.nameCompleate

    }
}
