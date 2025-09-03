package ir.part.app.signal.persian.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.schibsted.spain.barista.assertion.BaristaEnabledAssertions.assertEnabled
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListNotEmpty
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickBack
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem
import com.schibsted.spain.barista.interaction.BaristaListInteractions.scrollListToPosition
import com.schibsted.spain.barista.interaction.BaristaScrollInteractions
import com.schibsted.spain.barista.rule.flaky.AllowFlaky
import ir.part.app.signal.R.id.*
import ir.part.app.signal.R.string.*
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.helpers.CommonElements.waitForView
import ir.part.app.signal.helpers.ViewInteractions.assertWithTimeout
import ir.part.app.signal.helpers.testLinkReportWithId
import ir.part.app.signal.screens.AddRemoveBookmarkScreen
import ir.part.app.signal.screens.SearchScreen
import ir.part.app.signal.screens.ShareCheckNewsScreen
import ir.part.app.signal.utils.CustomViewMatchers.recyclerViewAtPositionOnView
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.containsString
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class BondTests : BaseTest() {
    @Before
    fun beforeTestsRun() {
        waitForView(rv_main_item, 10000)
        onView(
            allOf(
                withId(tv_see_all),
                hasSibling(withText("بازارها"))
            )
        ).perform(click())
        clickListItem(rv_main_item, 8)
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkMarketAndTrade() = testLinkReportWithId(testCase = 39555) {
        assertWithTimeout(9000)
        {
            assertDisplayed(rv_bond_market_state_best)
            assertListNotEmpty(rv_bond_market_state_best)
        }
        assertDisplayed(label_stock_market_state)
        assertDisplayed(label_stock_index)
        assertDisplayed(label_stock_market_value)
        assertDisplayed(label_number_of_trade)
        assertDisplayed(label_value_of_trade)
        assertDisplayed(label_volume_of_trade)

        assertDisplayedAtPosition(
            rv_bond_market_state_best,
            2,
            label_stock_market_best_trade_volume
        )

        assertDisplayedAtPosition(
            rv_bond_market_state_best,
            9,
            label_stock_market_best_trade_value
        )

        assertDisplayedAtPosition(
            rv_bond_market_state_best,
            16,
            label_stock_market_best_trade_number
        )

        scrollListToPosition(rv_bond_market_state_best, 3)
        clickListItem(rv_bond_market_state_best, 3)
        assertWithTimeout(11000)
        {
            assertEnabled(btn_chart_fullscreen)
            assertDisplayed(chart_bond)
            clickOn(btn_chart_fullscreen)
            clickBack()
            clickOn(label_market_status)
        }

        assertDisplayed(label_last_trade_price)
        assertDisplayed(label_ytmldp)
        assertDisplayed(label_final_price)
        assertDisplayed(label_ytmfp)
        assertDisplayed(label_maturity_date)

        clickOn(label_specs)

        assertDisplayed(label_deal_symbol_spec)
        assertDisplayed(label_bond_information)
        BaristaScrollInteractions.scrollTo(label_pillars_bonds)
        assertDisplayed(label_pillars_bonds)
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkBondInterestPeriod() = testLinkReportWithId(testCase = 39626) {
        assertWithTimeout(9000)
        {
            assertDisplayed(label_bond_interest_period)
            clickOn(label_bond_interest_period)
            assertListNotEmpty(rv_bond_maturity_date_period)

            onView(withId(rv_bond_maturity_date_period))
                .check(
                    matches(
                        recyclerViewAtPositionOnView(
                            2,
                            withText(containsString("%")),
                            tv_value
                        )
                    )
                )

            onView(
                allOf(withId(tv_sort_show), withText(label_ytm_sort), isDisplayed())
            )
                .perform(click())

            onView(withId(rv_bond_maturity_date_period))
                .check(
                    matches(
                        recyclerViewAtPositionOnView(
                            2,
                            withText(containsString("ریال")),
                            tv_value
                        )
                    )
                )
        }
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkBondInterestMaturityAndNews() = testLinkReportWithId(testCase = 39628) {
        assertWithTimeout(7000)
        {
            assertDisplayed(label_bond_interest_maturity)
            clickOn(label_bond_interest_maturity)
            assertListNotEmpty(rv_bond_maturity_date)
        }

        clickOn(fab_sort)
        assertDisplayed(rb_asc)
        clickOn(rb_asc)
        clickOn(btn_sort_apply)
        assertWithTimeout(6000)
        {
            assertListNotEmpty(rv_bond_maturity_date)

            ShareCheckNewsScreen().checkNewsAndShareIt(tl_bond, 3, 1)
        }
    }

    @Test
    fun addAndRemoveABondToBookmark() = testLinkReportWithId(testCase = 42020)
    {
        val bondTitle = "صکورش30"
        val result = "صکورش302"
        SearchScreen().search(bondTitle, result)
        AddRemoveBookmarkScreen().apply {
            addASymbolToBookmark(rv_search, 0, iv_star)
            checkSymbolInBookmark(text = result)
            removeASymbolFromBookmark(rv_bookmark, 0, iv_star)
        }
    }
}