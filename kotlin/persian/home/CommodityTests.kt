package ir.part.app.signal.persian.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListNotEmpty
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem
import com.schibsted.spain.barista.interaction.BaristaListInteractions.scrollListToPosition
import com.schibsted.spain.barista.rule.flaky.AllowFlaky
import ir.part.app.signal.R.id.*
import ir.part.app.signal.R.string.*
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.helpers.CommonElements.waitForView
import ir.part.app.signal.helpers.ViewInteractions.assertWithTimeout
import ir.part.app.signal.helpers.testLinkReportWithId
import ir.part.app.signal.screens.ShareCheckNewsScreen
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class CommodityTests : BaseTest() {

    @Before
    fun beforeTestsRun() {
        waitForView(rv_main_item, 10000)
        onView(
            allOf(
                withId(tv_see_all),
                hasSibling(withText("بازارها"))
            )
        ).perform(click())
        clickListItem(rv_main_item, 6)
    }

    @Test
    @AllowFlaky(attempts = 3)
    fun checkDetailsAboutAMetal() = testLinkReportWithId(testCase = 35425) {
        clickOn(label_global)
        assertWithTimeout(10000) {
            assertDisplayed(label_metal_and_mineral)
            assertDisplayed(label_basic_elements)
            assertDisplayed(rv_global_market)
            scrollListToPosition(rv_global_market, 8)
            assertDisplayed(label_ons)
            scrollListToPosition(rv_global_market, 13)
            assertDisplayed(label_mineral)
            clickOn(label_oil)
            scrollListToPosition(rv_global_market, 1)
            assertDisplayed(label_oil_alone)
            scrollListToPosition(rv_global_market, 8)
            assertDisplayed(label_gas)
            scrollListToPosition(rv_global_market, 12)
            assertDisplayed(label_opec)
            scrollListToPosition(rv_global_market, 18)
            assertDisplayed(label_petro)
            scrollListToPosition(rv_global_market, 24)
            assertDisplayed(label_energy)
        }
        clickOn(label_metal_and_mineral)

        scrollListToPosition(rv_global_market, 2)
        assertDisplayed(label_basic_elements)

        clickOn("الومینیوم")

        assertWithTimeout(7000) {
            assertDisplayed(tv_element_unit)
            assertDisplayed(tv_element_details_chart_title)

            clickOn(label_news)
            assertDisplayed(rv_news)
        }
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkCommodityExchangeAndCrops() = testLinkReportWithId(testCase = 35423) {
        assertWithTimeout(8000) {
            assertListNotEmpty(rv_iran_mercantile_exchange)
            assertDisplayed(label_commodity_exchange)
            clickOn(label_crops)
            assertDisplayed(label_saffron)
        }
        assertDisplayed(label_cumin)
        scrollListToPosition(rv_iran_mercantile_exchange, 17)
        assertDisplayed(label_pistachio)
        assertDisplayed(label_raisin)
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkCommodityNews() = testLinkReportWithId(testCase = 37143) {
        ShareCheckNewsScreen().checkNewsAndShareIt(tabLayout = tl_commodity, position = 0)
    }
}