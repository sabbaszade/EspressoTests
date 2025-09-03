package ir.part.app.signal.persian.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListNotEmpty
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickBack
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItemChild
import com.schibsted.spain.barista.interaction.BaristaListInteractions.scrollListToPosition
import com.schibsted.spain.barista.rule.flaky.AllowFlaky
import ir.part.app.signal.R.id.*
import ir.part.app.signal.R.string.*
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.helpers.CommonElements.waitForView
import ir.part.app.signal.helpers.ViewInteractions.assertWithTimeout
import ir.part.app.signal.helpers.testLinkReportWithId
import org.hamcrest.CoreMatchers.containsString
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class ForexTests : BaseTest() {

    @Before
    fun beforeTestsRun() {
        waitForView(rv_main_item, 10000)
        clickListItem(rv_main_item, 4)
    }

    @Test
    fun checkGlobalMarketTabInForex() = testLinkReportWithId(testCase = 37146)
    {
        assertWithTimeout(6000) {
            assertListNotEmpty(rv_global_market)
        }

        assertDisplayedAtPosition(
            rv_global_market,
            1,
            tv_global_stock_market_subject,
            "SPX"
        )
        assertDisplayedAtPosition(
            rv_global_market,
            2,
            tv_global_stock_market_subject,
            "DJI"
        )
        assertDisplayedAtPosition(
            rv_global_market,
            3,
            tv_global_stock_market_subject,
            "NDX"
        )
        assertDisplayedAtPosition(
            rv_global_market,
            4,
            tv_global_stock_market_subject,
            "DAX"
        )
        assertDisplayedAtPosition(
            rv_global_market,
            5,
            tv_global_stock_market_subject,
            "NI225"
        )

        clickListItem(rv_global_market, 3)
        assertDisplayed(tv_global_stock_market_subject, "NDX")
        assertDisplayed(tv_global_stock_market_full_name, "Nasdaq 100")
        assertWithTimeout(15000) {
            assertListNotEmpty(rv_index_components)
            assertDisplayedAtPosition(
                rv_index_components,
                0,
                tv_index_components_subject,
                "AAPL"
            )

            clickBack()
            scrollListToPosition(rv_global_market, 12)
        }
        clickListItemChild(rv_global_market, 12, iv_forex_star)

        assertWithTimeout(10000) {
            onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(containsString("به منتخب اضافه شد"))))
        }
        clickListItemChild(rv_global_market, 12, iv_forex_star)
        clickListItem(rv_global_market, 12)
        assertDisplayed(tv_forex_details_chart_title)
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkForexAnalysis() = testLinkReportWithId(testCase = 37148) {
        assertDisplayed(label_analysis)
        clickOn(label_analysis)
        assertWithTimeout(6000) {
            assertDisplayed(rv_analysis)
            assertListNotEmpty(rv_analysis)
            clickListItemChild(rv_analysis, 0, iv_more_Settings)
        }
        onView(withText(label_add_archive))
            .check(
                matches(
                    withEffectiveVisibility(
                        Visibility.VISIBLE
                    )
                )
            )
        onView(withText(label_share))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        clickBack()

        scrollListToPosition(rv_analysis, 5)
        scrollListToPosition(rv_analysis, 10)
        scrollListToPosition(rv_analysis, 15)
        scrollListToPosition(rv_analysis, 20)
    }
}