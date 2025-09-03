package ir.part.app.signal.persian.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListItemCount
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
import ir.part.app.signal.screens.ShareCheckNewsScreen
import ir.part.app.signal.utils.ConditionWatchers.waitForElement
import ir.part.app.signal.utils.checkDisplayed
import ir.part.app.signal.utils.viewWithId
import ir.part.app.signal.utils.wait
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class GoldAndCurrencyTests : BaseTest() {

    @Before
    fun beforeTestsRun() {
        waitForView(rv_main_item, 10000)
        clickListItem(rv_main_item, 3)
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkCurrency() = testLinkReportWithId(testCase = 23358) {
        assertDisplayed(label_analysis_gold_and_currency)

        val rvGoldCurrency = onView(withId(rv_gold_currency))
        waitForElement(rvGoldCurrency, 7000)

        assertWithTimeout(6000) {
            assertListNotEmpty(rv_gold_currency)
        }

        assertDisplayedAtPosition(rv_gold_currency, 1, tv_currency_subject, "دلار")
        repeat(5) {
            clickListItemChild(rv_gold_currency, it + 1, iv_currency_star)
        }
        val recyclerView: RecyclerView =
            activityRule.activity.findViewById(rv_gold_currency)

        recyclerView.adapter!!.itemCount

        clickOn(cv_more)
        assertWithTimeout(7000) {
            assertListNotEmpty(rv_currency)
            assertListItemCount(rv_currency, 32)
        }
        repeat(5) {
            clickListItemChild(rv_currency, it, iv_currency_star)
        }
        clickOn(label_market)
        assertWithTimeout(5000) {
            assertListItemCount(rv_currency, 32)

            clickOn(label_sana_currency)
            assertListItemCount(rv_currency, 17)

            clickOn(label_nima_currency)
            assertListItemCount(rv_currency, 17)
        }
        clickBack()
        clickListItem(rv_gold_currency, 1)
        assertWithTimeout(7000) {
            assertDisplayed(currency_details_chart)
        }
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkAnalysisAndNews() = testLinkReportWithId(testCase = 23363)
    {
        ShareCheckNewsScreen().checkNewsAndShareIt(tl_gold_currency_market, 1, 2)
        clickOn(label_analysis)
        assertWithTimeout(8000) {
            assertListNotEmpty(rv_analysis)
        }
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkCoinAndGold() = testLinkReportWithId(testCase = 23467) {
        assertWithTimeout(8000) {
            scrollListToPosition(rv_gold_currency, 8)
        }
        assertDisplayed(label_coin_gold)
        assertDisplayedAtPosition(rv_gold_currency, 8, tv_gold_subject, "سکه کامل")
        assertDisplayedAtPosition(rv_gold_currency, 9, tv_gold_subject, "نیم سکه")
        assertDisplayedAtPosition(rv_gold_currency, 10, tv_gold_subject, "ربع سکه")
        assertDisplayedAtPosition(rv_gold_currency, 11, tv_gold_subject, "هر گرم طلای 18 عیار")
        assertDisplayedAtPosition(rv_gold_currency, 12, tv_gold_subject, "آبشده نقدی (مظنه)")

        clickListItem(rv_gold_currency, 13)
        viewWithId(rv_gold_top_list).wait().checkDisplayed()

        val goldArray = arrayOf(
            "انس جهانی طلا",
            "هر گرم طلای 18 عیار",
            "آبشده نقدی (مظنه)",
            "هر گرم طلای 24 عیار",
            "مثقال(18عیار)",
            "هر گرم طلای 17 عیار"
        )

        repeat(6) {
            assertDisplayedAtPosition(rv_gold_top_list, it + 1, tv_gold_subject, goldArray[it])

        }
        assertDisplayedAtPosition(rv_gold_top_list, 8, tv_gold_subject, "انس نقره")
        assertDisplayedAtPosition(rv_gold_top_list, 9, tv_gold_subject, "گرم نقره 999")
        assertDisplayedAtPosition(rv_gold_top_list, 10, tv_gold_subject, "مثقال نقره 999")

        clickOn(label_coin)

        val coinArray = arrayOf("سکه کامل", "نیم سکه", "ربع سکه", "سکه (طرح قدیم)", "سکه یک گرمی")

        repeat(5) {
            assertDisplayedAtPosition(rv_coin_top_list, it + 1, tv_gold_subject, coinArray[it])
        }
    }
}