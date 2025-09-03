package ir.part.app.signal.screens

import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo
import ir.part.app.signal.R
import ir.part.app.signal.utils.checkDisplayed
import ir.part.app.signal.utils.viewWithText
import ir.part.app.signal.utils.wait

class SearchScreen {

    fun search(text: String, result: String) {
        clickOn(R.id.menu_search)

        writeTo(R.id.et_search, text)

        viewWithText(result).wait().checkDisplayed()

        assertDisplayedAtPosition(
            R.id.rv_search,
            0,
            R.id.tv_subject,
            result
        )
    }
}