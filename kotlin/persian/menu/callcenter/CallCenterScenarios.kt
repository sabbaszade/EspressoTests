package ir.part.app.signal.persian.menu.callcenter

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaDrawerInteractions.openDrawer
import ir.part.app.signal.R.id.*
import ir.part.app.signal.R.string.support_number
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.helpers.CommonElements.waitForView
import ir.part.app.signal.helpers.testLinkReportWithId
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CallCenterScenarios : BaseTest() {

    @Test
    fun verifyTappingOnPhoneNumberDialsPhone() = testLinkReportWithId(testCase = 35561) {
        val intent = Intent()
        val result =
            Instrumentation.ActivityResult(Activity.RESULT_OK, intent)
        intending(
            allOf(
                hasAction(Intent.ACTION_DIAL)
            )
        ).respondWith(result)

        waitForView(rv_main_item, 10000)
        openDrawer()
        waitForView(callCenterFragment, 3000)
        clickOn(callCenterFragment)
        assertDisplayed(tv_support_number, support_number)
        clickOn(tv_support_number)

        intended(
            allOf(
                hasAction(Intent.ACTION_DIAL),
                hasData("tel:02172423300#%23")
            )
        )
    }
}