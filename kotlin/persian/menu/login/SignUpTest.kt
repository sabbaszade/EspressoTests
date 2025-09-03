package ir.part.app.signal.persian.menu.login

import androidx.test.espresso.action.ViewActions.click
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.javafaker.Faker
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaDrawerInteractions.openDrawer
import ir.part.app.signal.R.id.*
import ir.part.app.signal.R.string
import ir.part.app.signal.R.string.menu_sign_in
import ir.part.app.signal.data.TestData
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.helpers.CommonElements.waitForView
import ir.part.app.signal.helpers.ViewInteractions.assertWithTimeout
import ir.part.app.signal.helpers.testLinkReportWithId
import ir.part.app.signal.utils.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignUpTest : BaseTest() {

    private val confirmBtn = viewWithId(mb_confirm)
    private val confirmCodeBtn = viewWithId(mb_confirm_code)
    private var milliseconds: Long = 25000
    private var myMobile = TestData.getMobile_fromNewUser()
    private var myPassword = TestData.getPassword_fromNewUser()
    private val etMobile = viewWithId(et_mobile_number)
    private val etFirstName = viewWithId(et_firstName)
    private val etLastName = viewWithId(et_lastName)
    private val etPassword = viewWithId(et_password)
    private val etRepeatPassword = viewWithId(et_repeat_password)

    var faker: Faker = Faker()
    var firstName: String = faker.name().firstName()
    var lastName: String = faker.name().lastName()

    @Before
    fun beforeTestsRun() {
        waitForView(cb_accept_agreement, 9000)
            .perform(click())
        clickOn(string.btn_start_app)

        waitForView(rv_main_item, 8000)
        assertWithTimeout(4000) {
            openDrawer()
        }
        clickOn(menu_sign_in)
    }

    @Test
    fun checkingRegistrationOfANewUser() = testLinkReportWithId(testCase = 41897) {
        waitForView(et_mobile_number, 10000)
        etMobile.type(myMobile).closeKeyboard()
        confirmBtn.click()

        waitForView(et_confirmation_code, 40000)
        Thread.sleep(milliseconds)
        confirmCodeBtn.click()

        waitForView(et_firstName, 30000)
        etFirstName.replace(firstName).closeKeyboard()
        etLastName.replace(lastName).closeKeyboard()
        etPassword.replace(myPassword).closeKeyboard()
        etRepeatPassword.replace(myPassword).closeKeyboard()
        confirmBtn.click()
    }
}