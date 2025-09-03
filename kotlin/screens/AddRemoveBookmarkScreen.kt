package ir.part.app.signal.screens

import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListNotEmpty
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItemChild
import ir.part.app.signal.R
import ir.part.app.signal.helpers.BaseTest.Companion.uiDevice
import ir.part.app.signal.helpers.CommonElements.showSnackBar
import ir.part.app.signal.helpers.CommonElements.waitForView
import ir.part.app.signal.helpers.ViewInteractions.assertWithTimeout

class AddRemoveBookmarkScreen {

    fun addASymbolToBookmark(list: Int, position: Int, starId: Int) {
        assertWithTimeout(10000)
        {
            assertListNotEmpty(list)
            clickListItemChild(list, position, starId)
            showSnackBar("به منتخب اضافه شد")
        }
    }

    fun removeASymbolFromBookmark(list: Int, position: Int, starId: Int) {
        assertWithTimeout(7000)
        {
            clickListItemChild(list, position, starId)
            showSnackBar("از منتخب حذف شد")
        }
    }

    fun checkSymbolInBookmark(subject: Int = R.id.tv_subject, text: String) {
        repeat(3) {
            uiDevice.pressBack()
        }
        waitForView(R.id.rv_main_item, 7000)
        assertWithTimeout(8000) {
            clickOn(R.id.bookmarkFragment)

            assertDisplayedAtPosition(
                R.id.rv_bookmark,
                0,
                subject,
                text
            )
        }
    }
}