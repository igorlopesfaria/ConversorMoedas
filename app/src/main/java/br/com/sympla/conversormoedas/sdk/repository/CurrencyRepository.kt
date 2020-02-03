package br.com.sympla.conversormoedas.sdk.repository

import br.com.sympla.conversormoedas.common.makeAsyncOperation
import br.com.sympla.conversormoedas.sdk.ConversorMoedasApi


class CurrencyRepository(val apiConversorMoedasApi: ConversorMoedasApi) {

    fun fetchCurrencies() = makeAsyncOperation() {
        apiConversorMoedasApi.fetchCurrenciesAsync().await()
    }

}