package ir.part.app.signal.persian.home

import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiSelector
import com.schibsted.spain.barista.assertion.BaristaClickableAssertions.assertClickable
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem
import com.schibsted.spain.barista.interaction.BaristaScrollInteractions.scrollTo
import com.schibsted.spain.barista.rule.flaky.AllowFlaky
import ir.part.app.signal.R.id.*
import ir.part.app.signal.R.string.*
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.helpers.CommonElements.waitForView
import ir.part.app.signal.helpers.testLinkReportWithId
import ir.part.app.signal.screens.ShareCheckNewsScreen
import ir.part.app.signal.utils.CustomViewMatchers.selectTabAtPosition
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class InsuranceTests : BaseTest() {

    @Before
    fun beforeTestsRun() {
        waitForView(rv_main_item, 10000)
        scrollTo(label_services)
        onView(
            allOf(
                withId(tv_see_all),
                hasSibling(withText("خدمات")),
                isCompletelyDisplayed(),
                withText(label_see_all),
            )
        ).perform(click())
        clickListItem(rv_main_item, 5)
    }

    @Test
    @AllowFlaky(attempts = 3)
    fun checkRegistrationLinkInTaminEjtemaeiInsurance() = testLinkReportWithId(testCase = 29277)
    {
        onView(withId(tl_insurance))
            .check(matches(isDisplayed()))

        onView(withId(tl_insurance)).perform(selectTabAtPosition(1))

        assertDisplayed(btn_tamin_ejtemaei_input)
        assertDisplayed(
            tv_tamin_ejtemaei_desc,
            label_tamin_ejtemaei_desc
        )
        waitForView(btn_tamin_ejtemaei_input, 4000)
        assertClickable(btn_tamin_ejtemaei_register)

        clickOn(btn_tamin_ejtemaei_register)
        val broker: UiObject = uiDevice.findObject(
            UiSelector().text("123ثبت نام")
                .packageName("com.android.chrome")
        )
        broker.exists()
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkLoginLinkToTaminEjtemaeiInsurance() = testLinkReportWithId(testCase = 29376) {
        onView(withId(tl_insurance))
            .check(matches(isDisplayed()))

        onView(withId(tl_insurance)).perform(selectTabAtPosition(1))

        assertDisplayed(btn_tamin_ejtemaei_input)
        assertDisplayed(
            tv_tamin_ejtemaei_desc,
            label_tamin_ejtemaei_desc
        )
        assertClickable(btn_tamin_ejtemaei_input)

        val expected = allOf(
            hasAction(Intent.ACTION_VIEW),
            hasData(("https://account.tamin.ir/auth/login"))
        )

        Intents.intending(expected).respondWith(Instrumentation.ActivityResult(0, null))

        onView(withId(btn_tamin_ejtemaei_input)).perform(click())

        Intents.intended(expected)
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkInsuranceNews() = testLinkReportWithId(testCase = 35427) {

        ShareCheckNewsScreen().checkNewsAndShareIt(
            tabLayout = tl_insurance,
            tabIndex = 0,
            position = 1
        )
    }
}