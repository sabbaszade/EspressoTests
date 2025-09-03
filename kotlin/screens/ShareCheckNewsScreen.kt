package ir.part.app.signal.screens

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiSelector
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListNotEmpty
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItemChild
import com.schibsted.spain.barista.interaction.BaristaListInteractions.scrollListToPosition
import ir.part.app.signal.R
import ir.part.app.signal.R.id.iv_more_Settings
import ir.part.app.signal.R.id.rv_news
import ir.part.app.signal.helpers.BaseTest.Companion.uiDevice
import ir.part.app.signal.helpers.CommonElements.waitForView
import ir.part.app.signal.helpers.ViewInteractions.assertWithTimeout
import ir.part.app.signal.utils.CustomViewMatchers.selectTabAtPosition
import org.hamcrest.CoreMatchers

class ShareCheckNewsScreen {
    fun checkNewsAndShareIt(tabLayout: Int, tabIndex: Int = 2, position: Int) {
        onView(withId(tabLayout))
            .perform(selectTabAtPosition(tabIndex))

        assertDisplayed(R.string.label_news)

        waitForView(rv_news, 9000)

        assertWithTimeout(10000) {
            assertListNotEmpty(rv_news)
        }

        clickListItemChild(rv_news, position, iv_more_Settings)
        if (onView(withText("اشتراک\u200Cگذاری")).isDisplayed()) {
            clickOn("اشتراک\u200Cگذاری")
        } else {
            clickOn(R.drawable.ic_share)
        }

        Intents.intending(IntentMatchers.hasAction(CoreMatchers.equalTo(Intent.ACTION_CALL)))
            .respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))

        Intents.intending(IntentMatchers.hasType("text/plain"))
            .respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))

        val share: UiObject = uiDevice.findObject(
            UiSelector().text("Share")
                .packageName("android")
        )
        share.exists()

        uiDevice.pressBack()
        waitForView(rv_news, 6000)
        scrollListToPosition(rv_news, 7)
    }

    fun ViewInteraction.isDisplayed(): Boolean {
        try {
            check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            return true
        } catch (e: NoMatchingViewException) {
            return false
        }
    }
}