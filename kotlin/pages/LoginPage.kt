package ir.part.app.signal.pages

import ir.part.app.signal.R
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.utils.checkDisplayed
import ir.part.app.signal.utils.viewWithText

class LoginPage : BaseTest() {
    val test_mobile = R.id.et_mobile_number
    val test_password = R.id.et_password

    val confirm_btn = R.id.mb_confirm
    val login_btn = R.id.mb_login

    val errTxt_emptyNotAllow = viewWithText(R.string.msg_empty_not_allow)
    val errTxt_mustBeElevenDigits = viewWithText(R.string.msg_mobile_number_must_be_eleven_digits)
    val errTxt_invalidMobile = viewWithText(R.string.msg_invalid_mobile)
    val errTxt_mustStartWithZero = viewWithText(R.string.msg_mobile_number_must_start_with_zero)
    val errTxt_shouldBetween6And25 =
        viewWithText(R.string.msg_should_between_six_and_twenty_five_chars)

    fun enterMobile(mobile: String): LoginPage {
        click(test_mobile)
        sendKeys(test_mobile, mobile)
        return this
    }

    fun enterPassword(password: String): LoginPage {
        click(test_password)
        sendKeys(test_password, password)
        return this
    }

    fun pressConfirmBtn() {
        click(confirm_btn)
    }

    fun pressLoginBtn(): HomePage {
        click(login_btn)
        return HomePage()
    }

    fun login(mobile: String, password: String): HomePage {
        enterMobile(mobile)
        pressConfirmBtn()
        enterPassword(password)
        return pressLoginBtn()
    }

    fun verifyErrorEmptyNotAllowIsDisplayed() {
        errTxt_emptyNotAllow.checkDisplayed()
    }

    fun verifyErrorMustBeElevenDigitsIsDisplayed() {
        errTxt_mustBeElevenDigits.checkDisplayed()
    }

    fun verifyErrorInvalidMobileIsDisplayed() {
        errTxt_invalidMobile.checkDisplayed()
    }

    fun verifyErrorMustStartWithZeroIsDisplayed() {
        errTxt_mustStartWithZero.checkDisplayed()
    }

    fun verifyErrorShouldBetween6and25IsDisplayed() {
        errTxt_shouldBetween6And25.checkDisplayed()
    }
}