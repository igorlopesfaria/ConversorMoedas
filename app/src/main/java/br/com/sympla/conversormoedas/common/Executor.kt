package br.com.sympla.conversormoedas.common

import br.com.arch.toolkit.livedata.response.MutableResponseLiveData
import br.com.arch.toolkit.livedata.response.ResponseLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

private val job = SupervisorJob()
private val scope = CoroutineScope(Dispatchers.IO + job)

fun <T> async(func: suspend () -> T) {
    scope.launch { func.invoke() }
}

fun <T> makeAsyncOperation(
    errorTransformer: (Throwable) -> Throwable,
    func: suspend () -> T
): ResponseLiveData<T> {
    val liveData = MutableResponseLiveData<T>()
    async {
        try {
            liveData.postLoading()
            liveData.postData(func.invoke())
        } catch (error: Throwable) {
            liveData.postError(errorTransformer.invoke(error))
        }
    }
    return liveData
}

fun <T> makeAsyncOperation(func: suspend () -> T): ResponseLiveData<T> {
    return makeAsyncOperation(
        errorTransformer = { error -> error },
        func = func
    )
}
