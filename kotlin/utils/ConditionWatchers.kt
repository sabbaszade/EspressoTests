package ir.part.app.signal.utils

import android.view.View
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.azimolabs.conditionwatcher.ConditionWatcher
import com.azimolabs.conditionwatcher.Instruction

object ConditionWatchers {
    /**
     * Waits for a [View], located by [ViewInteraction], to be displayed on the screen.
     */
    @Throws(Exception::class)
    @JvmStatic
    fun waitForElement(
        interaction: ViewInteraction,
        timeout: Int = 5000
    ): ViewInteraction {
        ConditionWatcher.setTimeoutLimit(timeout)
        ConditionWatcher.waitForCondition(object : Instruction() {

            override fun getDescription(): String {
                return "waitForElement"
            }

            override fun checkCondition(): Boolean {
                return try {
                    interaction.check(matches(isDisplayed()))
                    true
                } catch (ex: NoMatchingViewException) {
                    false
                }
            }
        })
        return interaction
    }

    /**
     * Waits for a [View], located by [ViewInteraction], to be gone.
     */
    @Throws(Exception::class)
    @JvmStatic
    fun waitForElementIsGone(
        interaction: ViewInteraction,
        timeout: Int = 5000
    ): ViewInteraction {
        ConditionWatcher.setTimeoutLimit(timeout)
        ConditionWatcher.waitForCondition(object : Instruction() {

            override fun getDescription(): String {
                return "waitForElementIsGone"
            }

            override fun checkCondition(): Boolean {
                return try {
                    interaction.check(matches(isDisplayed()))
                    false
                } catch (ex: NoMatchingViewException) {
                    true
                }
            }
        })
        return interaction
    }
}