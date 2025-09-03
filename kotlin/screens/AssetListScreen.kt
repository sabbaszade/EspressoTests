package ir.part.app.signal.screens

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnHolderItem
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToHolder
import androidx.test.espresso.matcher.ViewMatchers.*
import ir.part.app.signal.R
import ir.part.app.signal.R.id.*
import ir.part.app.signal.R.string.*
import ir.part.app.signal.screens.CustomViewActions.verifyAssetNotInTheList
import ir.part.app.signal.utils.withTitle

/**
 * Represents Asset list screen.
 */
class AssetListScreen {

    private val fabAddButton = onView(withId(fab_add_portfolio))
    private val assetList = onView(withId(rv_symbols))
    private val youHaveNoAssetsText = onView(withText(label_empty_portfolio))

    fun openAssetDetails(assetName: String): AssetDetailsScreen {
        assetList.perform(actionOnHolderItem(withTitle(assetName), click()))
        return AssetDetailsScreen()
    }

    fun clickAddFabButton(): AddEditAssetScreen {
        fabAddButton.perform(click())
        return AddEditAssetScreen()
    }

    fun verifyAssetIsDisplayed(assetItem: AssetItem?): AssetListScreen {
        assetList.perform(scrollToHolder<RecyclerView.ViewHolder>(withTitle(assetItem!!.name)))
        return this
    }

    fun verifyAssetItemNotShown(assetItem: AssetItem?): AssetListScreen {
        assetList.perform(verifyAssetNotInTheList(assetItem))
        return this
    }

    fun verifyAssetListScreenInitialState(): AssetListScreen {
        youHaveNoAssetsText.check(matches(isDisplayed()))
        fabAddButton.check(matches(isDisplayed()))
        return this
    }
}