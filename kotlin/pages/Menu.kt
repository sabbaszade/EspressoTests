package ir.part.app.signal.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import ir.part.app.signal.R
import ir.part.app.signal.data.TestData
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.helpers.CommonElements.isDisplayed
import ir.part.app.signal.utils.click
import ir.part.app.signal.utils.wait

class Menu : BaseTest() {
    val signIn = R.string.menu_sign_in
    val menu = onView(
        withContentDescription("باز کردن کشوی پیمایش")
    )
    val username = TestData.user.firstName + " " + TestData.user.lastName

    fun openMenu() {
        menu.wait().click()
    }

    fun clickSignIn() {
        if (onView(withText(signIn)).isDisplayed()) {
            clickOn(signIn)
        }
    }

    fun checkProfileName() {
        onView(withText(username)).isDisplayed()
    }

    fun closeMenu() {
        pressBack()
    }

    fun goToProfile() {
        click(username)
    }

}