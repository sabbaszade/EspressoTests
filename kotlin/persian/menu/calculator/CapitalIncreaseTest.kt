package ir.part.app.signal.persian.menu.calculator

import androidx.test.espresso.action.ViewActions.click
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickBack
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaDrawerInteractions.openDrawer
import com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo
import com.schibsted.spain.barista.rule.flaky.AllowFlaky
import ir.part.app.signal.R
import ir.part.app.signal.R.id.*
import ir.part.app.signal.R.string.*
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.helpers.CommonElements.waitForView
import ir.part.app.signal.helpers.testLinkReportWithId
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CapitalIncreaseTest : BaseTest() {

    @Before
    fun beforeTestRun() {
        waitForView(rv_main_item, 10000)
        openDrawer()
        waitForView(calculatorFragment, 10000)
            .perform(click())
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkCapitalIncreaseCalculation() = testLinkReportWithId(testCase = 37647) {
        clickOn(label_calculator_capital_increase)

        writeTo(et_last_price, "80000")
        writeTo(et_replete, "47")
        writeTo(et_cash_brought, "101")

        clickOn(btn_submit)

        assertDisplayed(tv_new_stock_price, "32,665.32")
        assertDisplayed(tv_priority_price, "31,665.32")

        // Leave the cash brought field blank
        clickBack()
        writeTo(et_cash_brought, "")

        clickOn(btn_submit)

        assertDisplayed(tv_new_stock_price, "54,421.77")
        assertDisplayed(tv_priority_price, "-")
    }
}