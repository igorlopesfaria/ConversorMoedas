package br.com.sympla.conversormoedas.currency

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.livedata.response.DataResultStatus
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.setup
import br.com.arch.toolkit.statemachine.state
import br.com.sympla.conversormoedas.currency.adapter.CurencyListAdapter
import br.com.sympla.conversormoedas.R
import br.com.sympla.conversormoedas.common.viewBind
import br.com.sympla.conversormoedas.exchange_rates.ExchangeRatesActivity
import br.com.sympla.conversormoedas.exchange_rates.ExchangeRatesActivity.Companion.EXTRA_CURRENCY_ITEM
import br.com.sympla.conversormoedas.exchange_rates.ExchangeRatesActivity.Companion.EXTRA_LIST
import br.com.sympla.conversormoedas.model.CurrencyListItemModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class CurrencyListActivity : AppCompatActivity() {

    private val stateMachine = ViewStateMachine()
    private val currencyViewModel: CurrencyViewModel by viewModel()

    private val toolbar by viewBind<Toolbar>(R.id.toolbar_activity)

    private val currencyList by viewBind<RecyclerView>(R.id.recycler_view_currency)
    private val buttonRetry by viewBind<AppCompatButton>(R.id.retry_button)
    private val placeholderView by viewBind<View>(R.id.placeholder_layout)
    private val errorView by viewBind<View>(R.id.error_layout)
    private val currencyAdapter: CurencyListAdapter =
        CurencyListAdapter(::selectCurrency)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_list)

        setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.title_main_currency)

        setupStateMachine()
        setObservers()
        listeners()

        currencyList.adapter = currencyAdapter
        currencyViewModel.fetchCurrencies()
    }

    private fun setupStateMachine() = stateMachine.setup {
        state(DataResultStatus.SUCCESS.ordinal) {
            visibles(currencyList)
            gones(errorView, placeholderView)
        }

        state(DataResultStatus.LOADING.ordinal) {
            visibles(placeholderView)
            gones(errorView, currencyList)
        }

        state(DataResultStatus.ERROR.ordinal) {
            visibles(errorView)
            gones(currencyList, placeholderView)
        }
    }

    private fun listeners() {

        buttonRetry.setOnClickListener {
            currencyViewModel.fetchCurrencies()
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        finish()
    }

    private fun setObservers() {
        currencyViewModel.currencyListLiveData.observe(this) {
            status(observer = ::changeViewState)
            data(observer = ::loadCurrencies)
        }
    }

    private fun loadCurrencies(list: List<CurrencyListItemModel>) =
        currencyAdapter.loadCurencyList(list)

    private fun changeViewState(status: DataResultStatus) =
        stateMachine.changeState(status.ordinal)


    fun selectCurrency(currencyListItemModel: CurrencyListItemModel) {
        currencyViewModel.currencyListLiveData.data?.let {

            val intent = Intent(this, ExchangeRatesActivity::class.java)
            intent.putExtra(EXTRA_CURRENCY_ITEM, currencyListItemModel)
            intent.putParcelableArrayListExtra(EXTRA_LIST, it as ArrayList )
            startActivity(intent)
            this.finish()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        stateMachine.shutdown()
    }
}
