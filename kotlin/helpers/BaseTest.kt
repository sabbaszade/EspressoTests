package ir.part.app.signal.helpers

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.view.View
import android.widget.TextView
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.*
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.schibsted.spain.barista.rule.flaky.FlakyTestRule
import ir.part.app.signal.features.home.ui.HomeActivity
import ir.part.app.signal.pages.*
import ir.part.app.signal.utils.*
import ir.part.app.signal.utils.EspressoIdlingResource.countingIdlingResource
import org.hamcrest.Matcher
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.RuleChain
import org.junit.rules.TestName
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
open class BaseTest {

    val activityRule: IntentsTestRule<HomeActivity> =
        IntentsTestRule(HomeActivity::class.java)
    private val flakyRule = FlakyTestRule()

    /**
     * Makes the current test name available inside test methods.
     */
    @get:Rule
    var testName = TestName()

    /**
     * Takes screenshot on test failure.
     */

    @get:Rule
    var chain: RuleChain = RuleChain.outerRule(flakyRule)
        .around(activityRule)
        // .around(GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE))
        .around(ScreenshotWatcher())

    @After
    open fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(countingIdlingResource)
    }

    companion object {
        /**
         * Signal test plan 4.x.x , Build prod_4.x.x
         */
        const val TEST_PLAN = 28302
        const val BUILD = 125

        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val uiDevice: UiDevice = UiDevice.getInstance(instrumentation)
        val context: Context = instrumentation.targetContext
        val res: Resources = context.resources
        val conf: Configuration = res.configuration

        val jsonData =
            context.resources.assets.open("loginUsers.json").bufferedReader().use { it.readText() }
        val outputJson = JSONObject(jsonData)

        var loginPage = LoginPage()
        var homePage = HomePage()
        var menu = Menu()
        var profile = Profile()
        val cryptoPage = CryptoPage()
    }

    @Before
    open fun setUp() {
        launch(HomeActivity::class.java)
        Espresso.setFailureHandler(
            CustomFailureHandler(
                InstrumentationRegistry.getInstrumentation().targetContext
            )
        )
        IdlingRegistry.getInstance().register(countingIdlingResource)
    }

    fun click(v: Int) {
        viewWithId(v).wait().click()
    }

    fun click(text: String) {
        viewWithText(text).wait().click()
    }

    fun sendKeys(v: Int, txt: String) {
        viewWithId(v).wait().replace(txt)
    }

    fun getText(matcher: ViewInteraction): String {
        var text = String()
        matcher.wait().perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "Text of the view"
            }

            override fun perform(uiController: UiController, view: View) {
                val tv = view as TextView
                text = tv.text.toString()
            }
        })

        return text
    }

}