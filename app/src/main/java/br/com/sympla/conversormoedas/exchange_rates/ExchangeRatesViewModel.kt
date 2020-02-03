package br.com.sympla.conversormoedas.exchange_rates

import br.com.arch.toolkit.livedata.response.ResponseLiveData
import br.com.arch.toolkit.livedata.response.SwapResponseLiveData
import br.com.sympla.conversormoedas.common.BaseViewModel
import br.com.sympla.conversormoedas.model.CurrencyListItemModel
import br.com.sympla.conversormoedas.model.ExchangeRateModel
import br.com.sympla.conversormoedas.sdk.repository.ExchangeRatesRepository

class ExchangeRatesViewModel(private val exchangeRatesRepository: ExchangeRatesRepository) :
    BaseViewModel() {

    var lCurrency: List<CurrencyListItemModel> = emptyList()
    var currencyItemSelect: CurrencyListItemModel? = null

    private val _exchangeRateLiveData = SwapResponseLiveData<ExchangeRateModel>()
    val exchangeRateListLiveData: ResponseLiveData<ExchangeRateModel>
        get() = _exchangeRateLiveData


    fun fetchExchangeRates() {

        if (lCurrency.isEmpty() || currencyItemSelect == null)
            return

        _exchangeRateLiveData.swapSource(
            exchangeRatesRepository.fetchExchangeRates(
                getListShortNames(),
                currencyItemSelect!!.shortName
            ), ::ExchangeRateModel
        )
    }

    private fun getListShortNames(): String {
        var listShortName: String = ""
        for (currencyItem in lCurrency) {

            if(!currencyItem.shortName.equals(currencyItemSelect!!.shortName))
                listShortName += currencyItem.shortName + ","
        }

        return listShortName.substring(0, listShortName.length - 1)
    }


}