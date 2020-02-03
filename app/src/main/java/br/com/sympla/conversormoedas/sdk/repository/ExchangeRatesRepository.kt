package br.com.sympla.conversormoedas.sdk.repository

import br.com.sympla.conversormoedas.common.makeAsyncOperation
import br.com.sympla.conversormoedas.sdk.ExchangeRatesApi
import br.com.sympla.conversormoedas.sdk.data.response.ExchangeRatesResponse


class ExchangeRatesRepository(val exchangeRatesApi: ExchangeRatesApi) {

    fun fetchExchangeRates(symbol: String, base: String) = makeAsyncOperation {
        exchangeRatesApi.fetchExchangeRatesAsync(symbol, base).await().body()?.rates
            ?: ExchangeRatesResponse(null, null, null, null, null)
    }

}