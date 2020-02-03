package br.com.sympla.conversormoedas.sdk.network

import android.util.Log
import br.com.sympla.conversormoedas.sdk.ExchangeRatesApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


object ExchangeRatesClientFactory : Interceptor {

    var okHttpClient = OkHttpClient.Builder()
        .addInterceptor(this)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS).build()

    fun build(url: String, moshi: Moshi): ExchangeRatesApi {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build().create(ExchangeRatesApi::class.java)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY


        val request = original.newBuilder()
            .method(original.method, original.body)
            .build()

        val requestLog = String.format(
            "Sending request %s on %s%n%s",
            request.url,
            chain.connection(),
            request.headers
        )

        Log.d("TAG", "request" + "\n" + requestLog);

        val buffer = Buffer()
        request.body?.writeTo(buffer)
        Log.d("TAG", "parameter" + "\n" + buffer.readUtf8());

        logResponse(
            chain.proceed(request)
        )
        return  chain.proceed(request)

    }

    private fun logResponse(response:Response) {
        var jsonResponse = "No Response"

        response.body?.run {
            jsonResponse = string()
        }
        Log.d(
            "TAG",
            "response" + "\nHEADERS\n" + response.headers + "\nBODY\n" + jsonResponse + "\nCODE\n" + response.code
        );

    }

}
