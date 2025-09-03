package ir.part.app.signal.helpers

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import ir.part.app.signal.utils.ConditionWatchers.waitForElement
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.containsString

object CommonElements {

    @JvmStatic
    fun waitForView(viewId: Int, timeout: Int = 10000): ViewInteraction {
        val view = onView(withId(viewId))
        return waitForElement(view, timeout)
    }

    @JvmStatic
    fun waitForView(text: String, timeout: Int): ViewInteraction {
        val view = onView(withText(text))
        return waitForElement(view, timeout)
    }

    @JvmStatic
    fun showSnackBar(text: String): ViewInteraction {
        return onView(
            allOf(
                withId(com.google.android.material.R.id.snackbar_text),
                withText(containsString(text))
            )
        )
            .check(matches(isDisplayed()))
    }
    fun ViewInteraction.isDisplayed(): Boolean {
        return try {
            check(matches(ViewMatchers.isDisplayed()))
            true
        } catch (e: NoMatchingViewException) {
            false
        }
    }
}