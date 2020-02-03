package br.com.sympla.conversormoedas.model

import android.os.Parcelable
import br.com.sympla.conversormoedas.sdk.data.response.ExchangeRatesResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
class ExchangeRateModel(
    val USD: Float?,
    val GBP: Float?,
    val CAD: Float?,
    val BRL: Float?,
    val EUR: Float?
):Parcelable{

    constructor(exchangeRatesResponse: ExchangeRatesResponse) : this(
        USD = exchangeRatesResponse.USD,
        GBP = exchangeRatesResponse.GBP,
        CAD = exchangeRatesResponse.CAD,
        BRL = exchangeRatesResponse.BRL,
        EUR = exchangeRatesResponse.EUR)

}