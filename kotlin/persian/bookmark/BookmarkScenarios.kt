package ir.part.app.signal.persian.bookmark

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListNotEmpty
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertTextColorIs
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickBack
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo
import com.schibsted.spain.barista.interaction.BaristaKeyboardInteractions.closeKeyboard
import com.schibsted.spain.barista.interaction.BaristaKeyboardInteractions.pressImeActionButton
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItemChild
import ir.part.app.signal.R.id.*
import ir.part.app.signal.R.string.*
import ir.part.app.signal.R.color.*
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.helpers.CommonElements.waitForView
import ir.part.app.signal.helpers.ViewInteractions.assertWithTimeout
import ir.part.app.signal.helpers.testLinkReportWithId
import ir.part.app.signal.utils.ConditionWatchers.waitForElement
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class BookmarkScenarios : BaseTest() {

    @Test
    fun testA_searchAndAddAllResultsToBookmark() = testLinkReportWithId(testCase = 18003) {
        waitForView(rv_main_item, 10000)

        clickOn(bookmarkFragment)
        waitForView(btn_bookmark_add, 5000)
            .perform(click())

        writeTo(et_search, "ملی")

        assertWithTimeout(10000) {
            val result = onView(withText("زپارس"))
            waitForElement(result)

            assertListNotEmpty(rv_search)
            pressImeActionButton()
            closeKeyboard()
        }

        val countSearch = activityRule.activity.findViewById<RecyclerView>(rv_search)
            .adapter?.itemCount!!

        for (i in 0 until countSearch - 1) {
            clickListItemChild(rv_search, i, iv_star)
        }

        clickBack()
        assertDisplayed(btn_bookmark_add)
        assertTextColorIs(btn_bookmark_add, exo_white)
    }

    @Test
    fun testB_deleteAllBookmarkAddedInPreviousTest() = testLinkReportWithId(testCase = 35474) {
        testA_searchAndAddAllResultsToBookmark()
        assertDisplayed(label_bookmark_description)
    }
}

