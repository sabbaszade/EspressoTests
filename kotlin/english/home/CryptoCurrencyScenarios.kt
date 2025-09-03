package ir.part.app.signal.english.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.schibsted.spain.barista.assertion.BaristaEnabledAssertions.assertEnabled
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertContains
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItemChild
import com.schibsted.spain.barista.interaction.BaristaListInteractions.scrollListToPosition
import com.schibsted.spain.barista.rule.flaky.AllowFlaky
import ir.part.app.signal.R.id.*
import ir.part.app.signal.R.string.*
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.helpers.CommonElements.showSnackBar
import ir.part.app.signal.helpers.CommonElements.waitForView
import ir.part.app.signal.helpers.ViewInteractions.assertWithTimeout
import ir.part.app.signal.helpers.testLinkReportWithId
import ir.part.app.signal.utils.ConditionWatchers.waitForElement
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters


@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class CryptoCurrencyScenarios : BaseTest() {

    @Before
    fun beforeTestsRun() {
        waitForView(rv_main_item, 10000)
        clickListItem(rv_main_item, 1)
        assertContains("Cryptocurrency")
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkMarketAndListOfCryptocurrencies() = testLinkReportWithId(testCase = 39711) {
        assertWithTimeout(7000) {
            assertDisplayed(label_crypto_currency_market_state)
            assertDisplayed(label_crypto_currency_market_cap)
            assertDisplayed(label_crypto_currency_vol24)
            assertDisplayed(label_crypto_currency_btc_dominance)
            assertDisplayed(label_crypto_currency_number_of_markets)

            assertDisplayedAtPosition(
                rv_cryptoCurrency_market_effective_list,
                2,
                label_crypto_market_best_market_cap
            )

            assertDisplayedAtPosition(
                rv_cryptoCurrency_market_effective_list,
                9,
                label_crypto_market_best_change_plus
            )

            assertDisplayedAtPosition(
                rv_cryptoCurrency_market_effective_list,
                16,
                label_crypto_market_best_change_minus
            )

            assertDisplayedAtPosition(
                rv_cryptoCurrency_market_effective_list,
                23,
                label_crypto_market_best_trade_volume
            )
        }
        scrollListToPosition(rv_cryptoCurrency_market_effective_list, 3)
        clickListItem(rv_cryptoCurrency_market_effective_list, 3)

        assertWithTimeout(9000) {
            assertDisplayed(crypto_currency_details_chart)
            assertEnabled(btn_comparison)
            assertEnabled(btn_chart_fullscreen)
        }

        clickOn(label_information)
        assertDisplayed(label_price_details)
        assertDisplayed(label_crypto_today)

        clickOn(label_news)
        assertDisplayed(label_coming_soon)
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkComparisonAndFullscreenChart() = testLinkReportWithId(testCase = 39724) {
        assertWithTimeout(15000) {
            assertDisplayedAtPosition(
                rv_cryptoCurrency_market_effective_list,
                2,
                label_crypto_market_best_market_cap
            )
            clickListItem(rv_cryptoCurrency_market_effective_list, 3)
            onView(withId(btn_chart_fullscreen)).check(matches(isClickable()))
        }
        clickOn(btn_chart_switch)
        assertDisplayed(crypto_currency_details_chart)

        assertWithTimeout(13000) {
            clickOn(btn_comparison)
            onView(
                allOf(withId(inc_stock_index), withText("TEDPIX"), isDisplayed())
            )
                .check(matches(isDisplayed()))
                .perform(click())
            onView(
                allOf(
                    withId(tv_comparison_chart_title),
                    isDisplayed()
                )
            )
                .check(matches(withText("Chart of Price Variations (%)")))
        }
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun addCryptocurrencyToWatchlist() = testLinkReportWithId(testCase = 35531)
    {
        clickOn(menu_search)
        writeTo(et_search, "bitcoin gold")

        assertWithTimeout(11000) {
            assertDisplayedAtPosition(
                rv_search,
                0,
                tv_subject,
                "Bitcoin Gold"
            )
        }

        clickListItem(rv_search, 0)

        val star = onView(withId(iv_crypto_currency_star))
        waitForElement(star, 3000)
        onView(withId(iv_crypto_currency_star)).check(matches(isClickable()))
        clickOn(iv_crypto_currency_star)

        showSnackBar("added to Watchlist")

        repeat(3) {
            uiDevice.pressBack()
        }

        waitForView(rv_main_item, 7000)
        assertWithTimeout(8000) {
            clickOn(bookmarkFragment)

            assertDisplayedAtPosition(
                rv_bookmark,
                0,
                tv_crypto_currency_subject,
                "Bitcoin Gold"
            )
            clickListItemChild(rv_bookmark, 0, iv_crypto_currency_star)
            showSnackBar("removed from Watchlist")
            assertDisplayed(label_bookmark_description)
        }
    }
}