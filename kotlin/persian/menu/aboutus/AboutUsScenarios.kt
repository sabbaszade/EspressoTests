package ir.part.app.signal.persian.menu.aboutus

import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaDrawerInteractions.openDrawer
import com.schibsted.spain.barista.interaction.BaristaScrollInteractions.scrollTo
import com.schibsted.spain.barista.rule.flaky.AllowFlaky
import ir.part.app.signal.R.id.*
import ir.part.app.signal.R.string.*
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.helpers.CommonElements.waitForView
import ir.part.app.signal.helpers.testLinkReportWithId
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AboutUsScenarios : BaseTest() {

    @Before
    fun beforeTestsRun() {
        waitForView(rv_main_item, 10000)
        openDrawer()
        clickOn(about_us)
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkAboutUs() = testLinkReportWithId(testCase = 37481) {
        assertDisplayed(iv_logo)
        assertDisplayed(tv_version)
        assertDisplayed(tv_about_us_text, about_text)
        assertDisplayed(tv_label_email, label_email_contact_us)
        assertDisplayed(tv_link_email, support_email)
        scrollTo(resource_label)
        assertDisplayed(tv_label_instagram, label_instagram)
        assertDisplayed(tv_link_instagram, instagram_link)
        assertDisplayed(tv_label_telegram, label_telegram)
        assertDisplayed(tv_signal_telegram, label_signal_telegram)
        assertDisplayed(tv_link_signal_telegram, signal_telegram_link)
        assertDisplayed(tv_analysis_telegram, label_analysis_telegram)
        assertDisplayed(tv_link_analysis_telegram, analysis_telegram_link)
        assertDisplayed(tv_crypto_currency_telegram, label_crypto_currency_telegram)
        assertDisplayed(tv_link_crypto_currency_telegram, crypto_currency_link)
        assertNotDisplayed(tv_label_signal, contact_signal_label)
        assertNotDisplayed(tv_link_signal, signal_link)
        assertDisplayed(resource_label)
        scrollTo(label_disclaimer)
        assertNotDisplayed(tv_label_faraBours, faraBours_label)
        assertNotDisplayed(tv_link_ifb, ifb_link)
        assertDisplayed(tv_label_tsetmc, tsetmc_label)
        assertDisplayed(tv_link_tsetmc, tsetmc_link)
        assertDisplayed(tv_label_tse, tse_label)
        assertDisplayed(tv_link_tse, tse_link)
        assertNotDisplayed(tv_label_rasamfunds, rasamfunds_label)
        assertNotDisplayed(tv_link_rasamfunds, rasamfunds_link)
        assertNotDisplayed(tv_label_sanarate, sanarate_label)
        assertNotDisplayed(tv_link_sanarate, sanarate_link)
        assertNotDisplayed(tv_label_irabo, irabo_label)
        assertNotDisplayed(tv_link_irabo, irabo_link)
        assertDisplayed(tv_label_investing, investing_label)
        assertDisplayed(tv_link_investing, investing_link)
        assertDisplayed(tv_label_tgju, tasnimnews_label)
        assertDisplayed(tv_link_tasnimnews, tasnimnews_link)
        assertDisplayed(label_disclaimer)
        scrollTo(label_privacy_more_info)
        assertDisplayed(tv_privacy_title, label_privacy)
        assertDisplayed(agreement_text)
        assertDisplayed(msg_sejam_permission)
        assertDisplayed(label_privacy_more_info)
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkTsetmcLink() = testLinkReportWithId(testCase = 37485) {
        scrollTo(label_disclaimer)
        assertDisplayed("http://www.tsetmc.com")

        val expected =
            allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData("http://www.tsetmc.com")
            )

        Intents.intending(expected).respondWith(Instrumentation.ActivityResult(0, null))

        onView(withId(tv_link_tsetmc)).perform(click())

        Intents.intended(expected)
    }
}