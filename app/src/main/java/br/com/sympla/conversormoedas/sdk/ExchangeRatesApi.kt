package br.com.sympla.conversormoedas.sdk

import br.com.sympla.conversormoedas.sdk.data.response.ExchangeRatesApiResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRatesApi{

    @GET("/latest")
    fun fetchExchangeRatesAsync(@Query("symbols", encoded = true) symbols: String, @Query("base") base: String): Deferred<Response<ExchangeRatesApiResponse>>

}