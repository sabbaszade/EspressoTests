package ir.part.app.signal.persian.programs

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaListInteractions.scrollListToPosition
import com.schibsted.spain.barista.rule.flaky.AllowFlaky
import ir.part.app.signal.R
import ir.part.app.signal.R.id.*
import ir.part.app.signal.R.string.*
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.helpers.CommonElements.waitForView
import ir.part.app.signal.helpers.ViewInteractions.assertWithTimeout
import ir.part.app.signal.helpers.testLinkReportWithId
import ir.part.app.signal.utils.CustomViewMatchers.forceClick
import ir.part.app.signal.utils.CustomViewMatchers.selectTabAtPosition
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class ProgramsScenarios : BaseTest() {

    @Test
    @AllowFlaky(attempts = 2)
    fun checkProgramsFragment() = testLinkReportWithId(testCase = 29374) {
        waitForView(rv_main_item, 10000)
        waitForView(programsFragment, 10000)

        val programs = onView(withId(programsFragment))
        programs.perform(forceClick())
        assertWithTimeout(15000) {
            assertDisplayed(rv_banner)
            assertDisplayed(rv_program)
        }
        scrollListToPosition(rv_banner, 5)
        scrollListToPosition(rv_banner, 9)

        onView(withId(tl_programs)).perform(selectTabAtPosition(1))
        onView(withId(tl_programs)).perform(selectTabAtPosition(0))
    }
}