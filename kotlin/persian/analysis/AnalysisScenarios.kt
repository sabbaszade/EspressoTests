package ir.part.app.signal.persian.analysis

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListNotEmpty
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItemChild
import com.schibsted.spain.barista.rule.flaky.AllowFlaky
import ir.part.app.signal.R.id.*
import ir.part.app.signal.R.string.*
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.helpers.ViewInteractions.assertWithTimeout
import ir.part.app.signal.helpers.testLinkReportWithId
import ir.part.app.signal.utils.ConditionWatchers.waitForElement
import ir.part.app.signal.utils.CustomViewMatchers.selectTabAtPosition
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class AnalysisScenarios : BaseTest() {

    @Test
    @AllowFlaky(attempts = 2)
    fun checkAnalysisFragment() = testLinkReportWithId(testCase = 21578) {
        uiDevice.waitForIdle()
        val analysis = onView(withId(analysisFragment))
        waitForElement(analysis, 30000)

        clickOn(analysisFragment)

        assertDisplayed(rv_analysis)
        assertDisplayed(label_iran_stock_market)
        assertDisplayed(label_global_market)
        assertDisplayed(label_crypto_currency)

        clickListItemChild(rv_analysis, 0, iv_more_Settings)
        assertDisplayed(label_add_archive)
        clickOn(label_add_archive)
        clickListItemChild(rv_analysis, 0, iv_more_Settings)
        assertDisplayed(label_remove_archive)
        clickOn(label_remove_archive)

        assertWithTimeout(15000) {
            assertListNotEmpty(rv_analysis)
            onView(withId(tl_analysis)).perform(selectTabAtPosition(1))
            assertListNotEmpty(rv_analysis)
            onView(withId(tl_analysis)).perform(selectTabAtPosition(2))
            assertListNotEmpty(rv_analysis)
            onView(withId(tl_analysis)).perform(selectTabAtPosition(0))
        }
    }
}