package ir.part.app.signal.persian.menu.login

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.schibsted.spain.barista.rule.flaky.AllowFlaky
import ir.part.app.signal.R.id.*
import ir.part.app.signal.R.string.*
import ir.part.app.signal.data.TestData
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.helpers.testLinkReportWithId
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import java.util.*


@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class LoginTests : BaseTest() {

    private var mobile = ""
    private var password = ""

    @Before
    fun before() {
        menu.apply {
            openMenu()
            clickSignIn()
        }
        mobile = TestData.getMobile_fromRegisteredUser()
        password = TestData.getPassword_fromRegisteredUser()
    }

    @After
    fun after() {
        homePage.verifyHomeIsDisplayed()
        menu.openMenu()
        menu.goToProfile()
        profile.pressLogout()
    }

    @Test
    fun checkLoginWithValidInput_thenLogout() = testLinkReportWithId(testCase = 41730)
    {
        loginPage.login(
            outputJson.getJSONObject("validUser").getString("mobile"),
            outputJson.getJSONObject("validUser").getString("password")
        )

        conf.locale = Locale("fa")
        res.updateConfiguration(
            conf,
            res.displayMetrics
        )

        val actualMarketTitle = homePage.getMarketsTitle()
        val expectedMarketTitle = context.resources.getString(label_markets)

        assertEquals(expectedMarketTitle, actualMarketTitle)
        homePage.verifyHomeIsDisplayed()
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkLoginWithInvalidInputs() = testLinkReportWithId(testCase = 29283) {
        loginPage.apply {
            enterMobile(
                outputJson.getJSONObject("invalidMobile")
                    .getString("noMobile")
            )
            pressConfirmBtn()
            verifyErrorEmptyNotAllowIsDisplayed()

            enterMobile(
                outputJson.getJSONObject("invalidMobile")
                    .getString("validWithoutZero")
            )
            pressConfirmBtn()
            verifyErrorMustStartWithZeroIsDisplayed()

            enterMobile(
                outputJson.getJSONObject("invalidMobile")
                    .getString("lessThan11Digit")
            )
            pressConfirmBtn()
            verifyErrorMustBeElevenDigitsIsDisplayed()

            enterMobile(
                outputJson.getJSONObject("invalidMobile")
                    .getString("zeroMobile")
            )
            pressConfirmBtn()
            verifyErrorInvalidMobileIsDisplayed()

            enterMobile(
                outputJson.getJSONObject("invalidMobile")
                    .getString("incorrect")
            )
            pressConfirmBtn()
            verifyErrorMustStartWithZeroIsDisplayed()

            enterMobile(mobile)
            pressConfirmBtn()

            enterPassword(
                outputJson.getJSONObject("invalidPassword")
                    .getString("noPassword")
            )
            pressLoginBtn()
            verifyErrorEmptyNotAllowIsDisplayed()

            enterPassword(
                outputJson.getJSONObject("invalidPassword")
                    .getString("incorrect")
            )
            pressLoginBtn()

            enterPassword(
                outputJson.getJSONObject("invalidPassword")
                    .getString("lessThan6Chars")
            )
            pressLoginBtn()
            verifyErrorShouldBetween6and25IsDisplayed()

            enterPassword(password)
            pressLoginBtn()
            homePage.verifyHomeIsDisplayed()
        }
    }
}