package ir.part.app.signal.persian.home


import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasType
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.UiWatcher
import com.schibsted.spain.barista.assertion.BaristaDrawerAssertions.assertDrawerIsClosed
import com.schibsted.spain.barista.assertion.BaristaDrawerAssertions.assertDrawerIsOpen
import com.schibsted.spain.barista.assertion.BaristaImageViewAssertions.assertHasAnyDrawable
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListNotEmpty
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickBack
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaDrawerInteractions.closeDrawer
import com.schibsted.spain.barista.interaction.BaristaDrawerInteractions.openDrawer
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItemChild
import com.schibsted.spain.barista.interaction.BaristaListInteractions.scrollListToPosition
import com.schibsted.spain.barista.interaction.BaristaScrollInteractions.scrollTo
import com.schibsted.spain.barista.rule.flaky.AllowFlaky
import ir.part.app.signal.R
import ir.part.app.signal.R.id.*
import ir.part.app.signal.R.string.*
import ir.part.app.signal.data.TestData
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.helpers.CommonElements.waitForView
import ir.part.app.signal.helpers.ViewInteractions.assertWithTimeout
import ir.part.app.signal.helpers.testLinkReportWithId
import ir.part.app.signal.screens.AddingAnItemToArchiveScreen
import ir.part.app.signal.screens.AssetDetailsScreen
import ir.part.app.signal.screens.ShareCheckNewsScreen
import ir.part.app.signal.utils.ConditionWatchers.waitForElement
import ir.part.app.signal.utils.CustomViewMatchers.selectTabAtPosition
import ir.part.app.signal.utils.CustomViewMatchers.setChecked
import ir.part.app.signal.utils.withTitle
import org.hamcrest.CoreMatchers.*
import org.junit.After
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters


@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class CryptoCurrencyTests : BaseTest() {
    @Before
    fun before() {
        registerUpdateDialogWatcher()
        homePage.apply {
            verifyHomeIsDisplayed()
            openCryptoCurrencySection()
        }
    }

    @After
    fun after() {
        removeUpdateDialogWatcher()
    }

    private fun registerUpdateDialogWatcher() {
        uiDevice.registerWatcher(
            "UpdateDialog",
            updateDialogWatcher
        )
        uiDevice.runWatchers()
    }

    private fun removeUpdateDialogWatcher() {
        uiDevice.removeWatcher("UpdateDialog")
    }

    companion object {
        val updateDialogWatcher = UiWatcher {

            val doNotShowButton: UiObject = uiDevice.findObject(
                UiSelector().text("عدم نمایش مجدد").className("android.widget.TextView")
                    .resourceId("ir.part.app.signal:id/label_do_not_show_again")
            )

            if (doNotShowButton.exists() && doNotShowButton.isEnabled) {
                doNotShowButton.click()
                return@UiWatcher true
            }
            false
        }
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkMarketStateAndListOfCryptoCurrencies() = testLinkReportWithId(testCase = 21497)
    {
        cryptoPage.apply {
            checkCryptoMarketState()
            checkMarketMapIcon()
            clickCryptoMarketMap()
            verifyMarketMapIsDisplayed()
            uiDevice.pressBack()
            verifyDollarPriceIsDisplayed()
            clickSwitchCryptoPrice()
            verifyToomanPriceIsDisplayed()

            checkCryptoCurrencyList()
        }
    }

    @Test
    fun checkAddingAnAnalysisToArchive() = testLinkReportWithId(testCase = 30359) {

        AddingAnItemToArchiveScreen().checkAddingAnAnalysisToArchive(tl_crypto_currency, 2)
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkNewsAndShareIt() = testLinkReportWithId(testCase = 30361) {

        ShareCheckNewsScreen().checkNewsAndShareIt(tl_crypto_currency, 1, 0)
    }

    @Test
    fun checkCryptoCurrencyCategory() = testLinkReportWithId(testCase = 41848)
    {
        val categories = listOf(
            "قرارداد هوشمند",
            "متاورس",
            "NFT",
            "میم کوین",
            "وب 3.0",
            "دیفای",
            "استیبل کوین",
            "پرایوسی کوین",
            "همه"
        )
        waitForView("قرارداد هوشمند", 5000)
        categories.forEach {
            scrollTo(it)
            clickOn(it)
            assertListNotEmpty(rv_cryptoCurrency_market_effective_list)
        }
    }

    @Test
    fun checkDetailsOfASymbol() {

        assertWithTimeout(7000) {
            assertDisplayedAtPosition(
                rv_cryptoCurrency_market_effective_list,
                3,
                tv_crypto_currency_subject,
                TestData.cryptoCurrency.englishName
            )
            assertDisplayed(TestData.cryptoCurrency.symbol!!)
            clickListItem(rv_cryptoCurrency_market_effective_list, 3)
            assertDisplayed(iv_crypto_currency_star)
            assertHasAnyDrawable(iv_crypto_currency_icon)
            assertDisplayed(btn_chart_fullscreen, label_chart_fullscreen)
            assertDisplayed(btn_comparison, label_comparison)
            assertDisplayed(
                tv_crypto_currency_details_chart_label_price,
                label_price_with_dollar
            )
        }
    }

    @Test
    fun clickOnACurrencyAndCheckDetails() = testLinkReportWithId(testCase = 42241) {
        assertWithTimeout(11000) {
            assertDisplayed(rv_cryptoCurrency_market_effective_list)
            assertListNotEmpty(rv_cryptoCurrency_market_effective_list)
        }
        clickListItem(rv_cryptoCurrency_market_effective_list, 8)

        openAssetDetails("Binance Coin")
    }

    private val assetList = onView(withId(rv_crypto_currency))

    private fun openAssetDetails(assetName: String): AssetDetailsScreen {
        assetList.perform(
            RecyclerViewActions.actionOnHolderItem(
                withTitle(assetName),
                click()
            )
        )
        return AssetDetailsScreen()
    }
}
