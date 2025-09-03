package ir.part.app.signal.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import ir.part.app.signal.R
import ir.part.app.signal.R.id.*
import ir.part.app.signal.R.string.*

/**
 * Represents the Asset item Details screen.
 */
class AssetDetailsScreen {

    private val assetPrice = onView(withId(tv_gold_price))
    private val assetName = onView(withId(tv_gold_subject))
    private val editAssetButton = onView(withId(btn_save_changes))
    private val deleteAssetButton = onView(withId(btn_delete))

    fun clickEditAssetButton(): AddEditAssetScreen {
        editAssetButton.perform(click())
        return AddEditAssetScreen()
    }

    fun deleteAsset(): AssetListScreen {
        deleteAssetButton.perform(click())
        return AssetListScreen()
    }

    fun verifyAssetDetails(assetItem: AssetItem): AssetDetailsScreen {
        assetName.check(matches(withText(assetItem.name)))
        assetPrice.check(matches(withText(assetItem.price)))
        return this
    }
}