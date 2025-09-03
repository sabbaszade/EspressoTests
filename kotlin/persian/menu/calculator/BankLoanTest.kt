package ir.part.app.signal.persian.menu.calculator

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
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
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class BankLoanTest : BaseTest() {

    @Before
    fun beforeTestRun() {
        waitForView(rv_main_item, 10000)
        openDrawer()
        waitForView(calculatorFragment, 10000)
            .perform(click())
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkBankLoanCalculation() = testLinkReportWithId(testCase = 35477) {
        clickOn(label_calculator_Loan)

        writeTo(et_loan_origin, "200000000")
        writeTo(et_loan_profit_per_year, "2")
        writeTo(et_loan_time, "12")

        clickOn(btn_submit)

        assertDisplayed(tv_calculate_title, label_loan_monthly_installment)
        assertDisplayed(tv_calculate_unit, "ریال")
        assertDisplayed(tv_calculate_value, "16,847,740")

        writeTo(et_loan_profit_per_year, "101")
        writeTo(et_loan_time, "")

        clickOn(btn_submit)

        onView(
            allOf(
                withId(textinput_error),
                withText(msg_should_between_one_and_one_hundred)
            )
        )
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(textinput_error),
                withText("این مورد نمی\u200Cتواند خالی باشد!")
            )
        )
            .check(matches(isDisplayed()))
    }
}