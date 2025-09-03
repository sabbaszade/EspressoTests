package ir.part.app.signal.persian.home.investment

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiSelector
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListNotEmpty
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickBack
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaDrawerInteractions.openDrawer
import com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem
import com.schibsted.spain.barista.rule.flaky.AllowFlaky
import ir.part.app.signal.R.id.*
import ir.part.app.signal.R.string.label_my_assets
import ir.part.app.signal.R.string.msg_portfolio_login
import ir.part.app.signal.data.TestData
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.helpers.CommonElements.waitForView
import ir.part.app.signal.helpers.ViewInteractions.assertWithTimeout
import ir.part.app.signal.helpers.testLinkReportWithId
import ir.part.app.signal.utils.CustomViewMatchers.forceClick
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class InvestmentTests : BaseTest() {

    private var mobile = ""
    private var password = ""


    @Before
    fun beforeTestsRun() {
        waitForView(rv_main_item, 10000)
        clickListItem(rv_main_item, 7)

        mobile = TestData.getMobile_fromRegisteredUser()
        password = TestData.getPassword_fromRegisteredUser()
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun loginToAccountAndCheckAssets() = testLinkReportWithId(testCase = 37151) {
        clickOn(tv_login)

        assertDisplayed(iv_portfolio_login)
        assertDisplayed(tv_portfolio_login, msg_portfolio_login)
        assertDisplayed(btn_portfolio_login)

        clickOn(btn_portfolio_login)

        assertDisplayed(et_mobile_number)
        clickOn(et_mobile_number)
        writeTo(et_mobile_number, mobile)
        onView(withId(mb_confirm))
            .perform(forceClick())

        waitForView(et_password, 7000)
        clickOn(et_password)
        writeTo(et_password, password)
        clickOn(mb_login)

        waitForView(tv_login, 20000)
        clickOn(tv_login)

        waitForView(et_password, 7000)
        clickOn(et_password)
        writeTo(et_password, password)
        clickOn(mb_login)

        assertWithTimeout(25000) {
            assertDisplayed(rv_symbols)
            assertDisplayed(tv_label, label_my_assets)
            assertDisplayed(fab_add_portfolio)
            assertDisplayedAtPosition(rv_symbols, 0, tv_name, "Bitcoin")
        }


        assertDisplayedAtPosition(
            rv_symbols,
            0,
            tv_stock_count,
            "1"
        )
        assertDisplayedAtPosition(
            rv_symbols,
            1,
            tv_name,
            "Ethereum"
        )
        assertDisplayedAtPosition(
            rv_symbols,
            1,
            tv_stock_count,
            "10"
        )
        assertDisplayedAtPosition(
            rv_symbols,
            2,
            tv_name,
            "دلار کانادا"
        )
        assertDisplayedAtPosition(
            rv_symbols,
            2,
            tv_stock_count,
            "2000"
        )
        assertDisplayedAtPosition(
            rv_symbols,
            3,
            tv_name,
            "سکه (طرح قدیم)"
        )
        assertDisplayedAtPosition(
            rv_symbols,
            3,
            tv_stock_count,
            "2"
        )
        assertDisplayedAtPosition(
            rv_symbols,
            4,
            tv_name,
            "مشترک آسمان یکم"
        )
        assertDisplayedAtPosition(
            rv_symbols,
            4,
            tv_stock_count,
            "100"
        )
        assertDisplayedAtPosition(rv_symbols, 5, tv_name, "شستا")
        assertDisplayedAtPosition(
            rv_symbols,
            5,
            tv_stock_count,
            "20"
        )
        assertDisplayedAtPosition(
            rv_symbols,
            6,
            tv_name,
            "زمین2"
        )
        assertDisplayedAtPosition(rv_symbols, 6, tv_price, "222")
        assertDisplayedAtPosition(rv_symbols, 7, tv_name, "زمین")
        assertDisplayedAtPosition(rv_symbols, 7, tv_price, "123")
        assertDisplayedAtPosition(rv_symbols, 8, tv_name, "زمین1")
        assertDisplayedAtPosition(rv_symbols, 8, tv_price, "111")
        assertDisplayedAtPosition(
            rv_symbols,
            9,
            tv_name,
            "دارایی ..."
        )
        assertDisplayedAtPosition(rv_symbols, 9, tv_price, "1")

        repeat(2) {
            clickBack()
        }

        assertWithTimeout(4000) {
            openDrawer()
        }
        assertDisplayed("ساناز عباس زاده")
        clickOn("ساناز عباس زاده")
        waitForView(mb_logout, 20000)
        clickOn(mb_logout)
        waitForView(rv_main_item, 11000)
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkBannerInvestmentFunds() = testLinkReportWithId(testCase = 41873) {
        clickOn(inc_banner_fund)
        assertWithTimeout(7000) {
            clickOn(btn_i_understand)
            assertListNotEmpty(rv_fund_list)
        }
    }

    @Test
    fun checkHamAfarinWebsite() = testLinkReportWithId(testCase = 41875) {
        clickOn(inc_banner_crowdfunding)
        val hamAfarin: UiObject = uiDevice.findObject(
            UiSelector().text("hamafarin.ir").packageName("com.android.chrome")
                .resourceId("com.android.chrome:id/url_bar")
        )

        hamAfarin.waitForExists(7000)
    }
}