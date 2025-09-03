package ir.part.app.signal.persian.home.investment

import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.screens.AssetItem
import ir.part.app.signal.screens.AssetListScreen
import org.junit.Test

/**
 * Validates ASSETs creation flows using Screen Object Pattern.
 */
class AddToDoTest : BaseTest() {

    @Test
    fun addsNewAsset() {
        AssetListScreen()
            .clickAddFabButton()
            .addNewAsset(assetItem)
            .verifyAssetIsDisplayed(assetItem)
    }

    @Test
    fun addsNewAssetWithoutPrice() {
        AssetListScreen()
            .clickAddFabButton()
            .typeAssetName(assetItem.name)
            .clickDoneFabButton()
            .verifyAssetIsDisplayed(assetItem)
    }

    @Test
    fun triesToAddEmptyAsset() {
        AssetListScreen()
            .clickAddFabButton()
            .addEmptyAsset()
            .verifySnackbarForEmptyAsset()
    }

    companion object {
        private var assetItem = AssetItem()
    }
}