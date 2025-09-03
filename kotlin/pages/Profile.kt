package ir.part.app.signal.pages

import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import ir.part.app.signal.R
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.utils.checkDisplayed
import ir.part.app.signal.utils.viewWithText

class Profile : BaseTest() {
    val changePassword = R.id.tv_change_password
    val oldPassword = R.id.et_old_password
    val newPassword = R.id.et_new_password
    val repeatNewPassword = R.id.et_repeat_new_password
    val save_btn = R.id.mb_save

    val errTxt_passwordsAreTheSame =
        viewWithText(R.string.msg_old_password_and_new_password_are_the_same)

    val errTxt_emptyNotAllow = R.string.msg_empty_not_allow
    val errTxt_shouldBetween6And25 = R.string.msg_should_between_six_and_twenty_five_chars


    fun pressChangePassword() {
        click(changePassword)
    }

    fun enterOldPassword(password: String): Profile {
        click(oldPassword)
        sendKeys(oldPassword, password)
        return this
    }

    fun enterNewPassword(password: String): Profile {
        click(newPassword)
        sendKeys(newPassword, password)
        return this
    }

    fun enterRepeatNewPassword(password: String): Profile {
        click(repeatNewPassword)
        sendKeys(repeatNewPassword, password)
        return this
    }

    fun pressSaveBtn() {
        click(save_btn)
    }

    fun pressLogout() {
        click(R.id.mb_logout)
    }

    fun verifyErrorSamePasswordsIsDisplayed() {
        errTxt_passwordsAreTheSame.checkDisplayed()
    }

    fun verifyErrorEmptyNotAllowIsDisplayed() {
        assertDisplayed(errTxt_emptyNotAllow)
    }

    fun verifyErrorShouldBetween6And25IsDisplayed() {
        assertDisplayed(errTxt_shouldBetween6And25)
    }

    fun changePassword(oldPass: String, newPass: String, repeatNew: String) {
        enterOldPassword(oldPass)
        enterNewPassword(newPass)
        enterRepeatNewPassword(repeatNew)
        pressSaveBtn()
    }
}