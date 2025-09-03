package ir.part.app.signal.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem
import ir.part.app.signal.R
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.utils.checkDisplayed
import ir.part.app.signal.utils.viewWithId
import ir.part.app.signal.utils.viewWithText
import ir.part.app.signal.utils.wait

class HomePage : BaseTest() {

    val home = viewWithId(R.id.rv_main_item)
    val markets = viewWithText(R.string.label_markets)
    val marketsViewInteraction = onView(withText(R.string.label_markets))
    val services = R.string.label_services

    fun getMarketsTitle(): String {
        return getText(marketsViewInteraction)
    }

    fun verifyHomeIsDisplayed() {
        home.wait().checkDisplayed()
    }

    fun openCryptoCurrencySection() {
        clickListItem(R.id.rv_main_item, 1)
    }
}