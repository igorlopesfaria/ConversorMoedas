package br.com.sympla.conversormoedas.currency

import br.com.arch.toolkit.livedata.extention.mapList
import br.com.arch.toolkit.livedata.response.ResponseLiveData
import br.com.arch.toolkit.livedata.response.SwapResponseLiveData
import br.com.sympla.conversormoedas.common.BaseViewModel
import br.com.sympla.conversormoedas.model.CurrencyListItemModel
import br.com.sympla.conversormoedas.sdk.repository.CurrencyRepository

class CurrencyViewModel(private val currencyRepository: CurrencyRepository) : BaseViewModel() {

    private val _currencyListLiveData = SwapResponseLiveData<List<CurrencyListItemModel>>()
    val currencyListLiveData: ResponseLiveData<List<CurrencyListItemModel>>
        get() = _currencyListLiveData


    fun fetchCurrencies() =
        _currencyListLiveData.swapSource(currencyRepository.fetchCurrencies().mapList {
            CurrencyListItemModel(it)
        })



}