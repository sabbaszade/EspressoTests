package ir.part.app.signal.english.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickBack
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem
import com.schibsted.spain.barista.rule.flaky.AllowFlaky
import ir.part.app.signal.R.id.*
import ir.part.app.signal.R.string.*
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.helpers.testLinkReportWithId
import ir.part.app.signal.utils.CustomViewMatchers.selectTabAtPosition
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StockScenarios : BaseTest() {
    @Test
    @AllowFlaky(attempts = 2)
    fun checkStocksTabsInEnglish() = testLinkReportWithId(testCase = 35533) {
        clickListItem(rv_main_item, 2)

        onView(withId(tl_stock))
            .perform(selectTabAtPosition(1))
        assertDisplayed(stock_tools_tab)
        assertDisplayed(label_filters)
        assertDisplayed(label_precedence)

        onView(withId(tl_stock))
            .perform(selectTabAtPosition(2))
        assertDisplayed(label_news)
        assertDisplayed(label_coming_soon)

        onView(withId(tl_stock))
            .perform(selectTabAtPosition(3))
        assertDisplayed(label_analysis)
        assertDisplayed(label_coming_soon)

        clickBack()
    }
}