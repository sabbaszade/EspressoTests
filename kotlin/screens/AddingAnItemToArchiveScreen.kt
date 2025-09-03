package ir.part.app.signal.screens

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.schibsted.spain.barista.assertion.BaristaDrawerAssertions
import com.schibsted.spain.barista.assertion.BaristaDrawerAssertions.assertDrawerIsClosed
import com.schibsted.spain.barista.assertion.BaristaDrawerAssertions.assertDrawerIsOpen
import com.schibsted.spain.barista.assertion.BaristaListAssertions
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListNotEmpty
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickBack
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaDrawerInteractions
import com.schibsted.spain.barista.interaction.BaristaDrawerInteractions.closeDrawer
import com.schibsted.spain.barista.interaction.BaristaDrawerInteractions.openDrawer
import com.schibsted.spain.barista.interaction.BaristaListInteractions
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItemChild
import ir.part.app.signal.R
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.helpers.CommonElements
import ir.part.app.signal.helpers.CommonElements.waitForView
import ir.part.app.signal.helpers.ViewInteractions
import ir.part.app.signal.helpers.ViewInteractions.assertWithTimeout
import ir.part.app.signal.helpers.testLinkReportWithId
import ir.part.app.signal.utils.*
import ir.part.app.signal.utils.CustomViewMatchers.selectTabAtPosition

class AddingAnItemToArchiveScreen {

    private val analysisList = viewWithId(R.id.rv_analysis)

    fun checkAddingAnAnalysisToArchive(tabId: Int, tabIndex: Int) {

        goToAnalysisTab(tabId, tabIndex)
        clickMore()
        addToArchive()
        checkArchive()
        removeFromArchive()
    }

    private fun goToAnalysisTab(tabId: Int, tabIndex: Int) {
        onView(withId(tabId))
            .perform(selectTabAtPosition(tabIndex))

        analysisList.wait().checkDisplayed()

        assertWithTimeout(15000) {
            assertListNotEmpty(R.id.rv_analysis)
        }
    }

    private fun clickMore() {
        clickListItemChild(R.id.rv_analysis, 0, R.id.iv_more_Settings)
        assertDisplayed(R.string.label_share)
        assertDisplayed(R.string.label_add_archive)
    }

    private fun addToArchive() {
        clickOn(R.string.label_add_archive)
        clickBack()
    }

    private fun checkArchive() {
        ConditionWatchers.waitForElement(
            onView(withId(R.id.rv_main_item)),
            8000
        )
        openDrawer()
        assertDisplayed(R.string.label_content_archive)
        assertDrawerIsOpen()

        clickOn(R.string.label_content_archive)
        assertDisplayed(R.id.rv_content_archive)
    }

    private fun removeFromArchive() {
        clickListItemChild(
            R.id.rv_content_archive,
            0,
            R.id.iv_more_Settings
        )
        assertDisplayed(R.string.label_remove_archive)
        clickOn(R.string.label_remove_archive)
        assertDisplayed(
            R.id.tv_empty_result,
            R.string.msg_empty_result_content_archive
        )
        assertDisplayed(
            R.id.tv_empty_result_description,
            R.string.msg_empty_result_content_archive_description
        )

        BaseTest.uiDevice.pressBack()
        openDrawer()
        closeDrawer()
        assertNotDisplayed(R.string.label_content_archive)
        assertDrawerIsClosed()
    }
}