package ir.part.app.signal.persian.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListItemCount
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListNotEmpty
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItemChild
import com.schibsted.spain.barista.interaction.BaristaListInteractions.scrollListToPosition
import com.schibsted.spain.barista.rule.flaky.AllowFlaky
import ir.part.app.signal.R.id.*
import ir.part.app.signal.R.string.*
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.helpers.CommonElements.waitForView
import ir.part.app.signal.helpers.ViewInteractions.assertWithTimeout
import ir.part.app.signal.helpers.testLinkReportWithId
import ir.part.app.signal.screens.ShareCheckNewsScreen
import ir.part.app.signal.utils.ConditionWatchers.waitForElement
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class RealEstateTests : BaseTest() {

    @Before
    fun beforeTestsRun() {
        waitForView(rv_main_item, 10000)
        onView(
            allOf(
                withId(tv_see_all),
                hasSibling(withText("بازارها"))
            )
        ).perform(click())
        clickListItem(rv_main_item, 7)
    }

    @Test
    @AllowFlaky(attempts = 3)
    fun checkTeseAndTemelli() = testLinkReportWithId(testCase = 26662) {
        assertWithTimeout(9000)
        {
            assertDisplayed(rv_real_estate_filter_list)
            assertListNotEmpty(rv_real_estate_filter_list)
            assertDisplayed(label_tese_filter)
            assertDisplayedAtPosition(
                rv_real_estate_filter_list,
                6,
                label_temelli_filter
            )
        }

        clickListItemChild(rv_real_estate_filter_list, 1, iv_real_estate_star)

        repeat(2) {
            uiDevice.pressBack()
        }

        clickOn(label_menu_item_bookmark)

        clickListItemChild(rv_bookmark, 0, iv_real_estate_star)
    }

    @Test
    fun checkConvertMortgageToRent() = testLinkReportWithId(testCase = 28024) {
        assertDisplayed(btn_real_state_loan_calculator)
        clickOn(btn_real_state_loan_calculator)

        assertDisplayed(label_convert_mortgage_to_rent)

        writeTo(et_current_mortgage_price, "200000000")
        writeTo(et_current_rent_price, "500000")

        // less mortgage price
        onView(withId(et_new_mortgage_price))
            .perform(typeText("150000000"), closeSoftKeyboard())

        onView(withId(et_new_mortgage_price))
            .perform(typeText("150000000"), closeSoftKeyboard())

        val newRent = onView(withText("1,750,000"))
        waitForElement(newRent, 6000)

        onView(withId(et_new_mortgage_price))
            .perform(clearText())
            .perform(typeText("15000000"), closeSoftKeyboard())

        assertWithTimeout(5000) {
            assertDisplayed("5,125,000")
        }

        // more mortgage price
        onView(withId(et_current_mortgage_price))
            .perform(clearText())
            .perform(typeText("50000000"), closeSoftKeyboard())

        onView(withId(et_current_rent_price))
            .perform(clearText())
            .perform(typeText("100000"), closeSoftKeyboard())

        onView(withId(et_new_mortgage_price))
            .perform(clearText())
            .perform(typeText("54000000"), closeSoftKeyboard())

        onView(withId(et_new_mortgage_price))
            .perform(clearText())
            .perform(typeText("54000000"), closeSoftKeyboard())

        assertWithTimeout(8000) {
            assertDisplayed(et_new_rent_price, "0")
        }

        // same price for all
        onView(withId(et_current_mortgage_price))
            .perform(clearText())
            .perform(typeText("9999999999999999"), closeSoftKeyboard())

        onView(withId(et_current_rent_price))
            .perform(clearText())
            .perform(typeText("9999999999999999"), closeSoftKeyboard())

        onView(withId(et_new_mortgage_price))
            .perform(clearText())
            .perform(typeText("9999999999999999"), closeSoftKeyboard())

        onView(withId(et_new_mortgage_price))
            .perform(clearText())
            .perform(typeText("9999999999999999"), closeSoftKeyboard())

        assertWithTimeout(8000) {
            assertDisplayed(et_new_rent_price, "10,000,000,000,000,000")
        }
        // Zero for current mortgage
        onView(withId(et_current_mortgage_price))
            .perform(clearText())
            .perform(typeText("0"), closeSoftKeyboard())

        onView(withId(et_current_rent_price))
            .perform(clearText())
            .perform(typeText("5000000"), closeSoftKeyboard())

        onView(withId(et_new_mortgage_price))
            .perform(clearText())
            .perform(typeText("50000000"), closeSoftKeyboard())

        onView(withId(et_new_mortgage_price))
            .perform(clearText())
            .perform(typeText("50000000"), closeSoftKeyboard())

        assertWithTimeout(8000) {
            assertDisplayed(et_new_rent_price, "3,750,000")
        }
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkBuyingMortgageBonds() = testLinkReportWithId(testCase = 28303) {
        assertDisplayed(btn_real_state_loan_calculator)
        clickOn(btn_real_state_loan_calculator)
        assertDisplayed(label_calculator_housing)

        assertDisplayed(label_buy_mortgage_bonds)
        clickOn(label_buy_mortgage_bonds)
        writeTo(et_required_mortgage_price, "500000000")

        clickOn(et_desired_bonds)

        val etSearch = onView(withId(et_search))
        waitForElement(etSearch, 3000)


        assertDisplayed(bond_list)
        clickOn("تسه1401")
        assertWithTimeout(6000) {
            assertDisplayed(et_required_bonds_count, "100")
        }
    }

    @Test
    fun checkNewsAndTutorialInRealStateLoan() = testLinkReportWithId(testCase = 28377)
    {
        assertDisplayed(btn_real_state_loan, label_real_state_loan)
        clickOn(btn_real_state_loan)

        assertDisplayed(label_news)
        assertDisplayed(label_tutorial)

        assertWithTimeout(7000) {
            assertDisplayed(rv_news)
            assertListNotEmpty(rv_news)
        }

        clickOn(label_tutorial)
        assertWithTimeout(6000) {
            assertListNotEmpty(rv_tutorial)
        }
        assertListItemCount(rv_tutorial, 8)
        scrollListToPosition(rv_tutorial, 7)
        clickListItem(rv_tutorial, 7)
    }

    @Test
    fun checkRealEstateNews() = testLinkReportWithId(testCase = 37141) {
        assertDisplayed(label_news)
        clickOn(label_news)
        ShareCheckNewsScreen().checkNewsAndShareIt(tl_real_estate, 1, 0)
    }
}