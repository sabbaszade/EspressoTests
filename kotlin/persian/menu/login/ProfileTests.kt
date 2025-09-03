package ir.part.app.signal.persian.menu


import ir.part.app.signal.data.TestData
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.helpers.testLinkReportWithId
import org.junit.Test

class ProfileTests : BaseTest() {

    @Test
    fun checkChangePasswordInProfile() = testLinkReportWithId(testCase = 30202) {
        val password = TestData.getPassword_fromRegisteredUser()
        val incorrect = outputJson.getJSONObject("invalidPassword")
            .getString("incorrect")
        val noPassword = outputJson.getJSONObject("invalidPassword")
            .getString("noPassword")
        val correctNew = outputJson.getJSONObject("correctPassword")
            .getString("newCorrectPass")

        val lessThan6Chars = outputJson.getJSONObject("invalidPassword")
            .getString("lessThan6Chars")

        menu.apply {
            openMenu()
            clickSignIn()
        }

        loginPage.login(
            outputJson.getJSONObject("validUser").getString("mobile"),
            outputJson.getJSONObject("validUser").getString("password")
        )

        menu.apply {
            openMenu()
            checkProfileName()
            goToProfile()
        }

        profile.apply {
            pressChangePassword()

            changePassword(password, password, password)
            verifyErrorSamePasswordsIsDisplayed()

            changePassword(incorrect, incorrect, incorrect)
            verifyErrorSamePasswordsIsDisplayed()

            changePassword(noPassword, correctNew, correctNew)
            verifyErrorEmptyNotAllowIsDisplayed()

            changePassword(password, noPassword, correctNew)
            verifyErrorEmptyNotAllowIsDisplayed()

            changePassword(password, correctNew, noPassword)
            verifyErrorEmptyNotAllowIsDisplayed()

            changePassword(password, noPassword, noPassword)
            verifyErrorEmptyNotAllowIsDisplayed()

            changePassword(noPassword, noPassword, noPassword)
            verifyErrorEmptyNotAllowIsDisplayed()

            changePassword(password, correctNew, correctNew)
            pressChangePassword()

            changePassword(correctNew, password, password)
            pressChangePassword()

            changePassword(password, lessThan6Chars, lessThan6Chars)
            verifyErrorShouldBetween6And25IsDisplayed()

            uiDevice.pressBack()
            pressLogout()
        }
        homePage.verifyHomeIsDisplayed()
    }
}