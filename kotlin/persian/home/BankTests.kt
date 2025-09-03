package ir.part.app.signal.persian.home


import android.app.Instrumentation
import android.content.Intent
import android.os.Build
import androidx.test.InstrumentationRegistry
import androidx.test.InstrumentationRegistry.getInstrumentation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiSelector
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListItemCount
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListNotEmpty
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertTextColorIs
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertTextColorIsNot
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItemChild
import com.schibsted.spain.barista.interaction.BaristaScrollInteractions.scrollTo
import com.schibsted.spain.barista.rule.flaky.AllowFlaky
import ir.part.app.signal.R.attr.colorTextPrimary
import ir.part.app.signal.R.attr.tabTextColorPrimary
import ir.part.app.signal.R.id.*
import ir.part.app.signal.R.string.*
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.helpers.CommonElements.waitForView
import ir.part.app.signal.helpers.ViewInteractions.assertWithTimeout
import ir.part.app.signal.helpers.testLinkReportWithId
import ir.part.app.signal.screens.ShareCheckNewsScreen
import ir.part.app.signal.utils.ConditionWatchers.waitForElement
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
class BankTests : BaseTest() {

    @Before
    fun beforeTestsRun() {
        waitForView(rv_main_item, 10000)
        clickListItem(rv_main_item, 11)
    }

    @Test
    @AllowFlaky(attempts = 5)
    fun inServicesCheckBankLoans() = testLinkReportWithId(testCase = 13552)
    {
        clickOn(bank_service_loans)
        assertDisplayed(real_state_loan)
        assertDisplayed(marriage_loan)
        assertDisplayed(types_of_Interest_free_loans)
        assertDisplayed(student_loans)
        assertDisplayed(self_employment_loan)
        assertDisplayed(other_loans)

        // real estate
        clickOn(real_state_loan)
        assertDisplayed(label_news)
        assertDisplayed(label_multi_media_tutorial)

        assertWithTimeout(5000) {

            assertListNotEmpty(rv_news)

            onView(withId(tl_bank_load))
                .perform(selectTabAtPosition(1))

            assertListNotEmpty(rv_tutorial)
        }

        uiDevice.pressBack()

        // interest free, self employment and other loans
        clickListItem(rv_main_item, 2)
        assertDisplayed(types_of_Interest_free_loans)
        assertListNotEmpty(rv_tutorial)

        uiDevice.pressBack()
        clickListItem(rv_main_item, 3)
        assertDisplayed(student_loans)
        assertListItemCount(rv_tutorial, 5)
        uiDevice.pressBack()

        clickListItem(rv_main_item, 4)
        assertDisplayed(self_employment_loan)
        assertWithTimeout(10000) {
            assertListItemCount(rv_tutorial, 3)

            uiDevice.pressBack()
            clickListItem(rv_main_item, 5)
            assertDisplayed(other_loans)
            assertListNotEmpty(rv_tutorial)
            uiDevice.pressBack()

            // marriage loan
            clickOn(marriage_loan)

            assertDisplayed(label_multi_media_tutorial)
            assertDisplayed(label_news)
            assertDisplayed(label_systems)

            onView(withId(tl_bank_load))
                .perform(selectTabAtPosition(1))
            assertListNotEmpty(rv_tutorial)

            assertTextColorIs(
                label_multi_media_tutorial,
                tabTextColorPrimary
            )
            assertTextColorIsNot(
                label_multi_media_tutorial,
                colorTextPrimary
            )

            onView(withId(tl_bank_load))
                .perform(selectTabAtPosition(0))
            assertListNotEmpty(rv_news)
        }
        assertTextColorIs(
            label_multi_media_tutorial,
            colorTextPrimary
        )

        assertTextColorIsNot(
            label_multi_media_tutorial,
            tabTextColorPrimary
        )

        clickOn(label_systems)
        onView(withId(tl_bank_load))
            .perform(selectTabAtPosition(2))

        assertDisplayed(loan_title_system)
        assertDisplayed("https://ve.cbi.ir/")
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun inServicesCheckSheba() = testLinkReportWithId(testCase = 29415) {
        clickOn(label_sheba)

        assertDisplayed(rv_banks)
        assertWithTimeout(20000) {
            assertListItemCount(rv_banks, 31)

            val expected = allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(("https://www.ba24.ir/services/sheba"))
            )

            intending(expected).respondWith(Instrumentation.ActivityResult(0, null))

            clickOn("آینده")
            val labelWaiting = onView(withText(label_waiting_for_topic_request))

            waitForElement(labelWaiting, 5000)
            // waitForElementIsGone(labelWaiting, 13000)

            Intents.intended(expected)
        }
    }

    @Test
    fun inServicesCheckNearbyBranch() = testLinkReportWithId(testCase = 29470) {
        clickOn(bank_service_nearest_branch)

        val bank = onView(withText("آینده"))
        waitForElement(bank, 6000)
        bank.perform(click())

        val labelWaiting = onView(withText(label_waiting_for_topic_request))
        waitForElement(labelWaiting, 10000)

        // UIAutomator - click permission dialog WHILE USING THE APP button.
        assertWithTimeout(9000) {

            val allowPermissions = uiDevice.findObject(
                UiSelector().resourceId("com.android.permissioncontroller:id/permission_allow_foreground_only_button")
                    .packageName("com.google.android.permissioncontroller")
            )
            if (allowPermissions.exists()) {
                allowPermissions.click()
            } else {
                allowPermissions.waitForExists(7000)
                allowPermissions.click()
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            with(getInstrumentation().uiAutomation) {
                executeShellCommand("appops set " + InstrumentationRegistry.getTargetContext().packageName + " android:mock_location allow")
                Thread.sleep(1000)
            }
        }
    }

    @Test
    fun calculateInterestOnBankDeposits() = testLinkReportWithId(testCase = 29530) {

        clickOn(label_calculator)
        clickOn(label_calculator_bank)
        clickOn(btn_submit)

        writeTo(et_profit_origin, "150000000")
        writeTo(et_profit_per_day, "18")
        writeTo(et_profit_time, "1")
        clickOn(btn_submit)

        assertWithTimeout(9000) {
            assertDisplayed(tv_calculate_value, "2,250,000")
            assertDisplayed(tv_calculate_compound_value, "2,250,000")
        }

        writeTo(et_profit_time, "2")
        clickOn(btn_submit)

        assertWithTimeout(9000) {
            assertDisplayed(tv_calculate_value, "4,500,000")
            assertDisplayed(tv_calculate_compound_value, "2,250,000")
        }
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkInformationAboutSelectedBank() = testLinkReportWithId(testCase = 29813) {
        assertWithTimeout(13000) {
            clickOn(label_banks)
            assertDisplayed(rv_bank)
            assertListItemCount(rv_bank, 31)

            clickListItem(rv_bank, 4)
            assertDisplayed(label_fund_type)
            assertDisplayed("غیردولتی تجاری")
            assertDisplayed(label_website)
            assertDisplayed("http://www.bpi.ir")
            assertDisplayed(label_bank_tel)
            assertDisplayed("021-82890")
            assertDisplayed(label_bank_ussd)
            assertDisplayed("*720#")
            assertDisplayed(tv_other_services, label_other_services)

            val expectedData = allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(("https://www.bpi.ir/otp"))
            )

            intending(expectedData).respondWith(Instrumentation.ActivityResult(0, null))

            clickOn(label_bank_one_time_password)

            Intents.intended(expectedData)
        }
    }

    @Test
    fun checkBankNewsAndShareIt() = testLinkReportWithId(testCase = 41857) {

        ShareCheckNewsScreen().checkNewsAndShareIt(tabLayout = tl_bank, position = 0)
    }

    @Test
    fun checkIcupWebsite() = testLinkReportWithId(testCase = 41859) {
        scrollTo(bank_service_cup_2)
        scrollTo(bank_service_cup_1)

        clickOn(bank_service_cup_1)

        val icup: UiObject = uiDevice.findObject(
            UiSelector().text("خدمات سفته الکترونیکی به صورت غیرحضوری")
                .packageName("com.android.chrome")
        )

        icup.waitForExists(3000)
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkListOfFundsWithFixedIncome() = testLinkReportWithId(testCase = 41861) {
        clickOn(cl_funds)
        val fundList: UiObject = uiDevice.findObject(
            UiSelector().resourceId("ir.part.app.signal:id/rv_fund_list")
        )
        fundList.waitForExists(7000)
        assertListNotEmpty(rv_fund_list)
        repeat(7) {
            clickListItemChild(rv_fund_list, it, iv_star)
        }
        repeat(7) {
            clickListItemChild(rv_fund_list, it, iv_star)
        }
    }
}
