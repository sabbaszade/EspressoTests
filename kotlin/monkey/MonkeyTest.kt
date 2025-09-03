package ir.part.app.signal.monkey

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import ir.part.app.signal.features.home.ui.HomeActivity
import ir.part.app.signal.helpers.testLinkReportWithId
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test class that demonstrates supervised monkey tests run.
 */
@RunWith(AndroidJUnit4::class)
class MonkeyTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<HomeActivity>()

    /**
     * Monkey tests will be executed against Signal application.
     */
    @Test
    fun testsInstrumentedApp() = testLinkReportWithId(testCase = 41950) {
        Monkey.run(50)
    }

    /**
     * Monkey tests will be executed against provided application package name.
     * This is the example of how to test 3rd party application.
     */
    @Test
    fun testsThirdPartyApp() {
        val packageName = "com.google.android.dialer"
        PackageInfo.launchPackage(packageName)
        Monkey.run(200, packageName)
    }
}