package br.com.sympla.conversormoedas.sdk.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrencyListItemResponse(
    val id: Int,
    val name: String,
    @Json(name = "short_name") val shortName: String,
    @Json(name = "flag") val flagUrlPath: String,
    var symbol: String
)
