package ir.part.app.signal.screens

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import ir.part.app.signal.R
import ir.part.app.signal.R.id.*
import ir.part.app.signal.R.string.*

/**
 * Represents both Create new ASSET and Edit existing ASSET screens.
 */
class AddEditAssetScreen {

    private val addAssetNameEditText = onView(withId(et_asset_name))
    private val addPriceEditText = onView(withId(et_price))
    private val doneFabButton = onView(withId(btn_add_portfolio))
    private val emptyAssetSnackbar = onView(withText(msg_empty_not_allow))

    fun typeAssetPrice(price: String): AddEditAssetScreen {
        addPriceEditText.perform(typeText(price), closeSoftKeyboard())
        return this
    }

    fun typeAssetName(assetName: String): AddEditAssetScreen {
        addAssetNameEditText.perform(typeText(assetName), closeSoftKeyboard())
        return this
    }

    fun updateAssetPrice(price: String): AddEditAssetScreen {
        addPriceEditText.perform(clearText(), typeText(price), closeSoftKeyboard())
        return this
    }

    fun updateAssetName(assetName: String): AddEditAssetScreen {
        addAssetNameEditText.perform(clearText(), typeText(assetName), closeSoftKeyboard())
        return this
    }

    /**
     * Add new ASSET flow
     */
    fun addNewAsset(assetItem: AssetItem?): AssetListScreen {
        typeAssetPrice(assetItem!!.price)
        typeAssetName(assetItem.name)
        clickDoneFabButton()
        return AssetListScreen()
    }

    /**
     * Edit existing ASSET flow
     */
    fun updateAsset(taskItem: AssetItem?): AssetListScreen {
        updateAssetPrice(taskItem!!.price)
        updateAssetName(taskItem.name)
        clickDoneFabButton()
        return AssetListScreen()
    }

    fun addEmptyAsset(): AddEditAssetScreen {
        clickDoneFabButton()
        return this
    }

    fun clickDoneFabButton(): AssetListScreen {
        doneFabButton.perform(click())
        return AssetListScreen()
    }

    fun clickBackButton(): AssetListScreen {
        Espresso.pressBack()
        return AssetListScreen()
    }

    fun verifySnackbarForEmptyAsset(): AddEditAssetScreen {
        emptyAssetSnackbar.check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        return this
    }
}