package br.com.sympla.conversormoedas.model

import android.os.Parcelable
import br.com.sympla.conversormoedas.sdk.data.response.CurrencyListItemResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
class CurrencyListItemModel(
    val id: Int,
    val name: String,
    val shortName: String,
    val symbol: String,
    val flagUrlPath:String):Parcelable{

    constructor(responseCurrencyListItem: CurrencyListItemResponse) : this(
        id = responseCurrencyListItem.id,
        shortName = responseCurrencyListItem.shortName,
        symbol = responseCurrencyListItem.symbol,
        name = responseCurrencyListItem.name,
        flagUrlPath = responseCurrencyListItem.flagUrlPath)

    val nameCompleate: String
        get() =  "${name} (${shortName})"

}