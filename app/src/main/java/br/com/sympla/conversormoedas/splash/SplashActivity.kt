package br.com.sympla.conversormoedas.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import br.com.sympla.conversormoedas.R
import br.com.sympla.conversormoedas.currency.CurrencyListActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()

        Handler().postDelayed({

            val intent = Intent(this, CurrencyListActivity::class.java)
            startActivity(intent)
            this.finish()

        }, 3000)
    }


}
