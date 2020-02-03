package br.com.sympla.conversormoedas

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import br.com.sympla.conversormoedas.sdk.network.MoshiFactory
import br.com.sympla.conversormoedas.currency.CurrencyViewModel
import br.com.sympla.conversormoedas.exchange_rates.ExchangeRatesViewModel
import br.com.sympla.conversormoedas.sdk.network.ConversorMoedasClientFactory
import br.com.sympla.conversormoedas.sdk.network.ExchangeRatesClientFactory
import br.com.sympla.conversormoedas.sdk.repository.CurrencyRepository
import br.com.sympla.conversormoedas.sdk.repository.ExchangeRatesRepository
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class ConversorMoedasApplication : Application() {
    private val viewModelModule = module {
        viewModel { CurrencyViewModel(get()) }
        viewModel { ExchangeRatesViewModel(get()) }

    }

    private val repositoryModule = module {
        single { CurrencyRepository(get()) }
        single { ExchangeRatesRepository(get()) }
    }

    private val networkModule = module {
        single { MoshiFactory.build() }

        single {
            ExchangeRatesClientFactory.build(
                BuildConfig.URL_API_EXCHANGE_RATE_API,
                get()//,
            )
        }

        single {
            ConversorMoedasClientFactory.build(
                BuildConfig.URL_API_CONVERSOR_MOEDA_API,
                get()//,
            )
        }


    }

    private val dbModule = module {
        single<SharedPreferences> {
            androidContext().getSharedPreferences(
                "SharedPreferencesConversorMoedas",
                Context.MODE_PRIVATE
            )
        }

//        single { AppPreferences(androidContext()) }
    }


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ConversorMoedasApplication)
            modules(listOf(repositoryModule, viewModelModule, networkModule, dbModule))
        }

    }
}