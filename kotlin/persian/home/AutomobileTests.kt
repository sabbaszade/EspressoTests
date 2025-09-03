package ir.part.app.signal.persian.home

import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.web.assertion.WebViewAssertions.webMatches
import androidx.test.espresso.web.sugar.Web.onWebView
import androidx.test.espresso.web.webdriver.DriverAtoms.findElement
import androidx.test.espresso.web.webdriver.DriverAtoms.getText
import androidx.test.espresso.web.webdriver.Locator
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiSelector
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListItemCount
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListNotEmpty
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickBack
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItemChild
import com.schibsted.spain.barista.interaction.BaristaScrollInteractions.scrollTo
import com.schibsted.spain.barista.rule.flaky.AllowFlaky
import ir.part.app.signal.R.id.*
import ir.part.app.signal.R.string.*
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.helpers.CommonElements.waitForView
import ir.part.app.signal.helpers.ViewInteractions.assertWithTimeout
import ir.part.app.signal.helpers.testLinkReportWithId
import ir.part.app.signal.utils.ConditionWatchers.waitForElement
import ir.part.app.signal.utils.CustomViewMatchers.selectTabAtPosition
import ir.part.app.signal.utils.CustomViewMatchers.setChecked
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.containsString
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class AutomobileTests : BaseTest() {

    @Before
    fun beforeTestsRun() {
        waitForView(rv_main_item, 10000)
        clickListItem(rv_main_item, 5)
    }

    /**
     * https://bama.ir/price
     * https://bazarkhodro.ir/price/signal/b
     * https://bazarkhodro.ir/price/signal/w
     */
    @Test
    @AllowFlaky(attempts = 2)
    fun checkPriceAndAutomakersWebsite() = testLinkReportWithId(testCase = 23575) {
        assertWithTimeout(12000)
        {
            onView(withId(rv_automaker)).check(matches(isDisplayed()))

            assertDisplayedAtPosition(
                rv_automaker,
                0,
                tv_automaker_official_website,
                label_official_website
            )
            assertDisplayedAtPosition(
                rv_automaker,
                0,
                tv_automaker_sales_plan,
                label_sales_plan
            )

            assertDisplayed(btn_automobile_price, automobilePrice)

            assertDisplayed("ایران خودرو")
            assertDisplayed("سایپا")
            scrollTo("مدیران خودرو")
            scrollTo("گروه بهمن")
        }

        clickOn(btn_automobile_price)

        checkWebElement("html/body/div/div/div/div/div/div/div[1]/div/h1", "قیمت روز خودرو")

        clickOn(btn_close)

        assertWithTimeout(7000)
        {
            assertListItemCount(rv_automaker, 4)
        }
        // website
        val expected = CoreMatchers.allOf(
            IntentMatchers.hasAction(Intent.ACTION_VIEW),
            IntentMatchers.hasData(("https://www.ikco.ir/"))
        )

        Intents.intending(expected).respondWith(Instrumentation.ActivityResult(0, null))

        clickListItemChild(rv_automaker, 0, tv_automaker_official_website)

        Intents.intended(expected)
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkCarEncyclopediaAndPreSale() = testLinkReportWithId(testCase = 23577) {

        onView(withId(tl_automobile)).perform(selectTabAtPosition(2))

        assertWithTimeout(7000)
        {
            onView(withId(rv_automobile_encyclopedia)).check(matches(isDisplayed()))

            assertDisplayed(label_sell_and_buy_guide)
            assertDisplayed(label_check_and_compare)
        }

        clickOn(label_sell_and_buy_guide)

        val rvTutorial = onView(withId(rv_tutorial))
        waitForElement(rvTutorial, 9000)

        assertWithTimeout(11000)
        {
            assertListNotEmpty(rv_tutorial)

            clickBack()
            clickOn(label_check_and_compare)

            waitForElement(rvTutorial, 9000)

            assertListNotEmpty(rv_tutorial)
        }
        clickBack()

        // pre sale
        onView(withId(tl_automobile)).perform(selectTabAtPosition(1))

        assertWithTimeout(7000)
        {
            onView(withId(rv_news)).check(matches(isDisplayed()))

            assertDisplayed(label_info_me_for_automobile_news)
            assertDisplayed(sm_topic)

            assertListNotEmpty(rv_news)
        }

        onView(withId(sm_topic))
            .perform(setChecked(true))
            .perform(setChecked(false))
            .perform(setChecked(true))
    }

    @Test
    fun checkBanner() = testLinkReportWithId(testCase = 42203) {
        waitForView("ایران خودرو", 7000)
        waitForView(inc_automobile_advertise_banner, 7000).perform(click())
        val broker: UiObject = uiDevice.findObject(
            UiSelector().text("ثبت نام غیر حضوری کارگزاری بورس")
                .packageName("com.android.chrome")
        )
        broker.exists()
    }

    private fun checkWebElement(value: String, text: String) {
        assertWithTimeout(15000)

        {
            assertDisplayed(wv_automobile_price)

            onWebView().forceJavascriptEnabled()

            onWebView()
                .withElement(
                    findElement(
                        Locator.XPATH,
                        value
                    )
                )
                .check(
                    webMatches(
                        getText(),
                        containsString(text)
                    )
                )
        }
    }
}