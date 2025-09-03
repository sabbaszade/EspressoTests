package ir.part.app.signal.english.home

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaDrawerInteractions.openDrawer
import com.schibsted.spain.barista.rule.flaky.AllowFlaky
import ir.part.app.signal.R.id.rv_main_item
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.helpers.CommonElements.waitForView
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EnglishSignal : BaseTest() {

    @Test
    @AllowFlaky(attempts = 2)
    fun checkToChangeLanguageOfApp() {
        uiDevice.waitForIdle()
        waitForView(rv_main_item, 10000)
        openDrawer()
        clickOn("English Language")
        waitForView(rv_main_item, 5000)
    }
}