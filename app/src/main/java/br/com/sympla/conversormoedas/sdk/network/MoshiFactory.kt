package br.com.sympla.conversormoedas.sdk.network

import com.squareup.moshi.Moshi

object MoshiFactory {

    fun build(): Moshi {
        return Moshi.Builder()
            .build()
    }
}
