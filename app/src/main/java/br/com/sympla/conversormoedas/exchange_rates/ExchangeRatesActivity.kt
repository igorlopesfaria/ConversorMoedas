package br.com.sympla.conversormoedas.exchange_rates

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.*
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.livedata.response.DataResultStatus
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.setup
import br.com.arch.toolkit.statemachine.state
import br.com.sympla.conversormoedas.exchange_rates.adapter.RateCurrencyListAdapter
import br.com.sympla.conversormoedas.R
import br.com.sympla.conversormoedas.common.CurrencyEditText
import br.com.sympla.conversormoedas.common.hideKeyboard
import br.com.sympla.conversormoedas.common.viewBind
import br.com.sympla.conversormoedas.currency.CurrencyListActivity
import br.com.sympla.conversormoedas.model.CurrencyListItemModel
import br.com.sympla.conversormoedas.model.ExchangeRateModel
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.github.siyamed.shapeimageview.CircularImageView
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel


class ExchangeRatesActivity : AppCompatActivity() {

    private val flagCurrencyIMG by viewBind<CircularImageView>(R.id.flag_currency_img)
    private val nameCurrencyTXT by viewBind<AppCompatTextView>(R.id.currency_name_txt)
    private val searchCurrencyIMG by viewBind<AppCompatImageView>(R.id.search_currency_img)
    private val moneyETX by viewBind<CurrencyEditText>(R.id.money_etx)
    private val symbolCurrencyTXT by viewBind<AppCompatTextView>(R.id.symbol_currency_txt)
    private val convertBTN by viewBind<AppCompatButton>(R.id.convert_btn)

    private val stateMachine = ViewStateMachine()
    private val exchangeRatesViewModel: ExchangeRatesViewModel by viewModel()
    private val rateCurrencyListAdapter: RateCurrencyListAdapter =
        RateCurrencyListAdapter()

    private val toolbar by viewBind<Toolbar>(R.id.toolbar_activity)

    private val rateCurrencyList by viewBind<RecyclerView>(R.id.recycler_view)
    private val buttonRetry by viewBind<AppCompatButton>(R.id.retry_button)
    private val placeholderView by viewBind<View>(R.id.placeholder_layout)
    private val errorView by viewBind<View>(R.id.error_layout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_rates)
        bindProgressButton(convertBTN)

        setupStateMachine()
        setObservers()
        listeners()

        setSupportActionBar(toolbar)


        rateCurrencyList.adapter = rateCurrencyListAdapter

        searchCurrencyIMG.setOnClickListener{
            startActivity( Intent(this, CurrencyListActivity::class.java))
        }
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private fun listeners() {

        buttonRetry.setOnClickListener {
            exchangeRatesViewModel.fetchExchangeRates()
        }

        convertBTN.setOnClickListener{
            it.hideKeyboard()
            exchangeRatesViewModel.fetchExchangeRates()
        }

    }


     private fun setupStateMachine() = stateMachine.setup {
        state(DataResultStatus.SUCCESS.ordinal) {
            visibles(rateCurrencyList)
            gones(errorView, placeholderView)

            onEnter {
                hideLoading()
            }

        }

        state(DataResultStatus.LOADING.ordinal) {
            visibles(placeholderView)
            gones(errorView, rateCurrencyList)
            onEnter {
                loadingButton()
            }
        }

        state(DataResultStatus.ERROR.ordinal) {
            visibles(errorView)
            gones(rateCurrencyList, placeholderView)

            onEnter {
                hideLoading()
            }
        }
    }

    private fun loadingButton() {
        convertBTN.showProgress {
            buttonText = getString(R.string.converting)
            progressColor = Color.WHITE
        }
        convertBTN.isClickable = false
    }

    private fun hideLoading() {
        convertBTN.hideProgress(R.string.converter)
        convertBTN.isClickable = true
    }


    private fun setObservers() {
        exchangeRatesViewModel.exchangeRateListLiveData.observe(this) {
            status(observer = ::changeViewState)
            data(observer = ::loadRateCurrencies)
        }
    }

    private fun loadRateCurrencies(exchangeRateModel: ExchangeRateModel) {
        val valueToConvert: String = moneyETX.currencyText.replace(".", "").replace(",", ".")

        rateCurrencyListAdapter.loadRateCurrencyList(
            exchangeRatesViewModel.lCurrency,
            exchangeRateModel,
            valueToConvert.toFloat()
        )
    }

    private fun changeViewState(status: DataResultStatus) =
        stateMachine.changeState(status.ordinal)



    override fun onResume() {
        super.onResume()

        intent.getParcelableExtra<CurrencyListItemModel>(EXTRA_CURRENCY_ITEM)?.let { currencyListItemModel ->
            bindMainCurrency(currencyListItemModel)
            exchangeRatesViewModel.currencyItemSelect = currencyListItemModel
        }

        intent.getParcelableArrayListExtra<CurrencyListItemModel>(EXTRA_LIST)?.let { lCurrencyListItemModel ->
            exchangeRatesViewModel.lCurrency = lCurrencyListItemModel
        }

        exchangeRatesViewModel.fetchExchangeRates()


    }

    private fun bindMainCurrency(currencyListItemModel: CurrencyListItemModel){
        Picasso.get().load(currencyListItemModel.flagUrlPath)
            .into(flagCurrencyIMG)

        nameCurrencyTXT.text = currencyListItemModel.name
        symbolCurrencyTXT.text = currencyListItemModel.symbol

    }
    override fun onDestroy() {
        super.onDestroy()
        stateMachine.shutdown()
    }

    companion object {
        const val EXTRA_CURRENCY_ITEM = "url"
        const val EXTRA_LIST = "list"
    }

}
