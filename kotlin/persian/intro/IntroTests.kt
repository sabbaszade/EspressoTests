package ir.part.app.signal.persian.intro

import androidx.core.widget.NestedScrollView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.schibsted.spain.barista.assertion.BaristaCheckedAssertions.assertChecked
import com.schibsted.spain.barista.assertion.BaristaCheckedAssertions.assertUnchecked
import com.schibsted.spain.barista.assertion.BaristaImageViewAssertions.assertHasAnyDrawable
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.rule.flaky.AllowFlaky
import ir.part.app.signal.R
import ir.part.app.signal.R.id.*
import ir.part.app.signal.R.string.*
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.helpers.CommonElements.waitForView
import ir.part.app.signal.helpers.ViewInteractions.assertWithTimeout
import ir.part.app.signal.helpers.testLinkReportWithId
import ir.part.app.signal.utils.ConditionWatchers.waitForElement
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class IntroTests : BaseTest() {

    @Test
    @AllowFlaky(attempts = 2)
    fun checkFragmentIntro() = testLinkReportWithId(testCase = 16279) {
        waitForView(cb_accept_agreement, 10000)

        assertUnchecked(cb_accept_agreement)

        clickOn(tv_agreement_message)

        assertDisplayed(
            tv_title_agreement,
            label_disclaimer
        )

        assertHasAnyDrawable(iv_agreement)

        clickOn(iv_close)
        clickOn(cb_accept_agreement)

        assertChecked(cb_accept_agreement)
        clickOn(cb_accept_agreement)

        onView(instanceOf(NestedScrollView::class.java))
            .perform(swipeUp())

        val startBtn = onView(withId(R.id.btn_start_app))
        waitForElement(startBtn, 5000)

        startBtn.perform(click())

        assertWithTimeout(7000) {
            clickOn(cb_accept_agreement)
            startBtn.perform(click())
        }
    }
}