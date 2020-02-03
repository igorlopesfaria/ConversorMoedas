package br.com.sympla.conversormoedas.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

open class BaseViewModel: ViewModel() {

    private val viewModelJob = Job()

    protected val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}