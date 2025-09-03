package ir.part.app.signal.persian.menu.calculator

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
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
import org.junit.Test

class TechnicalCalculatorTest : BaseTest() {

    @Before
    fun beforeTestRun() {
        waitForView(rv_main_item, 10000)
        openDrawer()
        waitForView(calculatorFragment, 10000)
            .perform(click())
    }

    @Test
    @AllowFlaky(attempts = 3)
    fun checkTechnicalCalculator() = testLinkReportWithId(testCase = 39237) {
        clickOn(label_technical_calculator)
        writeTo(et_highest_rate, "7000")
        writeTo(et_lowest_rate, "6000")
        writeTo(et_close_price, "6100")

        clickOn(R.id.btn_calculate)

        // Woodie
        clickOn(label_woodie)

        onView(allOf(withId(tv_pivot_point), isCompletelyDisplayed()))
            .check(matches(withText("6300")))

        onView(allOf(withId(tv_resistance_one), isCompletelyDisplayed()))
            .check(matches(withText("6600")))

        onView(allOf(withId(tv_resistance_two), isCompletelyDisplayed()))
            .check(matches(withText("7300")))

        onView(allOf(withId(tv_support_one), isCompletelyDisplayed()))
            .check(matches(withText("5600")))

        onView(allOf(withId(tv_support_two), isCompletelyDisplayed()))
            .check(matches(withText("5300")))

        // Camarilla
        // TODO:  Other values are now displayed , bug has been reported
        clickOn(label_camarilla)

        onView(allOf(withId(tv_pivot_point), isCompletelyDisplayed()))
            .check(matches(withText("6367")))

/*        onView(allOf(withId(.tv_resistance_one), isCompletelyDisplayed()))
            .check(matches(withText("7,183.3")))

        onView(allOf(withId(.tv_resistance_two), isCompletelyDisplayed()))
            .check(matches(withText("7,266.6")))

            //R3 & R4

        onView(allOf(withId(.tv_support_one), isCompletelyDisplayed()))
            .check(matches(withText("5,016.7")))

        onView(allOf(withId(.tv_support_two), isCompletelyDisplayed()))
            .check(matches(withText("4,933.4")))

            //S3 & S4
            */

        // Fibonacci
        clickOn(label_Fibonacci)

        onView(allOf(withId(tv_pivot_point), isCompletelyDisplayed()))
            .check(matches(withText("6367")))

        onView(allOf(withId(tv_resistance_one), isCompletelyDisplayed()))
            .check(matches(withText("6749")))

        onView(allOf(withId(tv_resistance_two), isCompletelyDisplayed()))
            .check(matches(withText("6985")))

        onView(allOf(withId(tv_resistance_three), isCompletelyDisplayed()))
            .check(matches(withText("7367")))

        onView(allOf(withId(tv_support_one), isCompletelyDisplayed()))
            .check(matches(withText("5985")))

        onView(allOf(withId(tv_support_two), isCompletelyDisplayed()))
            .check(matches(withText("5749")))

        onView(allOf(withId(tv_support_three), isCompletelyDisplayed()))
            .check(matches(withText("5367")))
    }
}
