package br.com.sympla.conversormoedas.sdk

import br.com.sympla.conversormoedas.sdk.data.response.CurrencyListItemResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ConversorMoedasApi{

    @GET("/bins/vb9oe")
    fun fetchCurrenciesAsync(): Deferred<List<CurrencyListItemResponse>>

}