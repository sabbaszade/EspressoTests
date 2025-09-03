package ir.part.app.signal.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.schibsted.spain.barista.assertion.BaristaImageViewAssertions.assertHasDrawable
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListItemCount
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListNotEmpty
import ir.part.app.signal.R
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.utils.CustomViewMatchers.recyclerViewAtPositionOnView
import ir.part.app.signal.utils.checkDisplayed
import ir.part.app.signal.utils.viewWithId
import ir.part.app.signal.utils.viewWithText
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.allOf

class CryptoPage : BaseTest() {

    val label_crypto_currency = viewWithText(R.string.label_crypto_currency)
    val label_crypto_currency_market_state =
        viewWithText(R.string.label_crypto_currency_market_state)
    val label_crypto_market_cap = viewWithText(R.string.label_crypto_market_cap)
    val label_crypto_currency_vol24 = viewWithText(R.string.label_crypto_currency_vol24)
    val label_crypto_currency_btc_dominance =
        viewWithText(R.string.label_crypto_currency_btc_dominance)
    val label_crypto_currency_number_of_markets =
        viewWithText(R.string.label_crypto_currency_number_of_markets)
    val label_market_map = viewWithText(R.string.label_market_map)
    val inc_crypto_market_map = R.id.inc_crypto_market_map
    val mmv_crypto = viewWithId(R.id.mmv_crypto)
    val rv_cryptoCurrency_list = R.id.rv_cryptoCurrency_market_effective_list
    val sw_crypto_price = R.id.sw_crypto_price
    val tv_crypto_currency_unit = R.id.tv_crypto_currency_unit

    val marketStateList = listOf(
        label_crypto_currency,
        label_crypto_currency_market_state,
        label_crypto_market_cap,
        label_crypto_currency_vol24,
        label_crypto_currency_btc_dominance,
        label_crypto_currency_number_of_markets,
        label_market_map
    )

    fun checkCryptoMarketState() {
        marketStateList.forEach {
            it.checkDisplayed()
        }
    }

    fun clickCryptoMarketMap() {
        click(inc_crypto_market_map)
    }

    fun verifyMarketMapIsDisplayed() {
        mmv_crypto.checkDisplayed()
    }

    fun checkMarketMapIcon() {
        assertHasDrawable(
            R.id.iv_market_market,
            R.drawable.ic_market_map
        )
    }

    fun checkCryptoListNotEmpty(){
        viewWithId(rv_cryptoCurrency_list).checkDisplayed()
        assertListNotEmpty(rv_cryptoCurrency_list)
        assertListItemCount(rv_cryptoCurrency_list, 30)
    }

    fun checkCryptoCurrencyList() {
        checkCryptoListNotEmpty()
        assertDisplayedAtPosition(
            rv_cryptoCurrency_list,
            2,
            R.string.label_crypto_market_best_market_cap
        )

        assertDisplayedAtPosition(
            rv_cryptoCurrency_list,
            9,
            R.string.label_crypto_market_best_change_plus
        )

        assertDisplayedAtPosition(
            rv_cryptoCurrency_list,
            16,
            R.string.label_crypto_market_best_change_minus
        )

        assertDisplayedAtPosition(
            rv_cryptoCurrency_list,
            23,
            R.string.label_crypto_market_best_trade_volume
        )
    }

    fun clickSwitchCryptoPrice() {
        onView(allOf(withId(sw_crypto_price), isCompletelyDisplayed())).checkDisplayed()
            .perform(click())
    }

    fun verifyCorrectPriceIsDisplayed(text: String) {
        onView(withId(rv_cryptoCurrency_list))
            .check(
                matches(
                    recyclerViewAtPositionOnView(
                        3,
                        withText(CoreMatchers.containsString(text)),
                        tv_crypto_currency_unit
                    )
                )
            )
    }

    fun verifyToomanPriceIsDisplayed() {
        verifyCorrectPriceIsDisplayed("میل")
    }

    fun verifyDollarPriceIsDisplayed() {
        verifyCorrectPriceIsDisplayed("دلار")
    }

}