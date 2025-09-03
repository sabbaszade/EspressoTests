package ir.part.app.signal.persian.home

import android.app.Instrumentation
import android.content.Intent.ACTION_VIEW
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListNotEmpty
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem
import com.schibsted.spain.barista.interaction.BaristaScrollInteractions.scrollTo
import com.schibsted.spain.barista.rule.flaky.AllowFlaky
import ir.part.app.signal.R.id.*
import ir.part.app.signal.R.string.*
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.helpers.CommonElements.waitForView
import ir.part.app.signal.helpers.ViewInteractions.assertWithTimeout
import ir.part.app.signal.helpers.testLinkReportWithId
import ir.part.app.signal.screens.AddRemoveBookmarkScreen
import ir.part.app.signal.utils.checkDisplayed
import ir.part.app.signal.utils.viewWithText
import ir.part.app.signal.utils.wait
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class FundsTests : BaseTest() {

    private var fundTitle = "صندوق"

    @Before
    fun beforeTestsRun() {
        waitForView(rv_main_item, 10000)
        clickListItem(rv_main_item, 6)
    }

    @Test
    fun checkSearchProcessForASpecificFund() = testLinkReportWithId(testCase = 29936) {
        clickOn(menu_search)

        writeTo(et_search, "سپهر تدبیرگران")

        viewWithText(fundTitle).wait().checkDisplayed()

        assertDisplayedAtPosition(
            rv_search,
            0,
            tv_subject,
            "مشترک سپهر تدبیرگران"
        )
        assertDisplayedAtPosition(rv_search, 0, tv_tag, "صندوق")

        clickListItem(rv_search, 0)
        assertDisplayed(label_chart_market_price_changes)
        assertDisplayed(label_chart_fullscreen)
    }

    @Test
    fun checkInvestmentFunds() = testLinkReportWithId(testCase = 28977) {

        assertWithTimeout(7000) {
            assertDisplayed(rv_fund_list)
            assertListNotEmpty(rv_fund_list)
        }
        val sortingOptions = listOf(
            label_max_week_return_value,
            label_max_month_return_value,
            label_max_three_month_return_value,
            label_max_six_month_return_value,
            label_max_total_return_value,
            label_max_nav,
            label_min_nav,
            label_max_year_return_value
        )
        clickOn(label_max_year_return_value)

        assertDisplayed(label_sort_by)
        sortingOptions.forEach {
            clickOn(it)
        }

        clickOn(btn_sort_apply)

        assertWithTimeout(11000) {
            assertDisplayed(rv_fund_list)
            assertListNotEmpty(rv_fund_list)
        }
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkDetailsOfAFund() = testLinkReportWithId(testCase = 29006) {

        assertWithTimeout(10000) {
            assertListNotEmpty(rv_fund_list)
            clickListItem(rv_fund_list, 1)

            assertDisplayed(tv_title_chart, label_chart_market_price_changes)
        }
        assertDisplayed(tv_label_fund_efficiency2, label_fund_efficiency)
        scrollTo(fund_asset_chart)
        assertDisplayed(fund_asset_chart)

        clickOn(label_fund_information)

        assertDisplayed(tv_title_fund_info, label_fund_information)
        assertDisplayed(cv_fund_info)

        assertDisplayed(label_fund_update_date)
        assertDisplayed(label_fund_total_nav)
        assertDisplayed(label_fund_unit_no)

        assertDisplayed(tv_title_fund_spec, label_fund_spec)
        assertDisplayed(cv_fund_spec)

        assertDisplayed(label_fund_type)
        assertDisplayed(label_fund_manager)

        scrollTo(label_codal_announcement)

        assertDisplayed(label_fund_activity_start_date)
        assertDisplayed(label_fund_liquidity_guarantor)
        assertDisplayed(label_fund_site)
        assertDisplayed(label_fund_auditor)
        assertDisplayed(label_fund_trustee)
        assertDisplayed(label_fund_phone)
    }

    @Test
    @AllowFlaky(attempts = 3)
    fun checkWebsiteOfAFund() = testLinkReportWithId(testCase = 29008) {
        waitForView(menu_search, 5000)
        clickOn(menu_search)
        writeTo(et_search, "مشترک آسمان")
        assertWithTimeout(15000)
        {
            clickOn("مشترک آسمان خاورمیانه")
            clickOn(label_fund_information)
        }
        scrollTo(label_fund_site)
        assertDisplayed("https://akmfund.ir")

        val expected =
            allOf(hasAction(ACTION_VIEW), hasData("https://akmfund.ir"))

        intending(expected).respondWith(Instrumentation.ActivityResult(0, null))

        clickOn("https://akmfund.ir")

        Intents.intended(expected)
    }

    @Test
    fun addRemoveAFundToBookmark() = testLinkReportWithId(testCase = 29012)
    {
        AddRemoveBookmarkScreen().addASymbolToBookmark(rv_fund_list, 1, iv_star)
        AddRemoveBookmarkScreen().removeASymbolFromBookmark(rv_fund_list, 1, iv_star)
    }
}
