package br.com.sympla.conversormoedas.sdk.data.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExchangeRatesApiResponse(
    val date: String,
    val base: String,
    var rates: ExchangeRatesResponse
)

data class ExchangeRatesResponse (
    val USD: Float?,
    val GBP: Float?,
    val CAD: Float?,
    val BRL: Float?,
    val EUR: Float?
)
