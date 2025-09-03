package ir.part.app.signal.utils;

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.internal.util.Checks
import ir.part.app.signal.core.util.binding.DataBoundViewHolder
import ir.part.app.signal.databinding.ItemCryptoCurrencyBinding
import org.hamcrest.Description
import org.hamcrest.Matcher

/**
 * RecyclerView matchers that match ASSET items in assets list.
 */

fun withTitle(symbolName: String): Matcher<RecyclerView.ViewHolder> {
    Checks.checkNotNull(symbolName)
    return object :
        BoundedMatcher<RecyclerView.ViewHolder, DataBoundViewHolder<ViewDataBinding>>(
            DataBoundViewHolder::class.java
        ) {
        override fun matchesSafely(item: DataBoundViewHolder<ViewDataBinding>): Boolean {
            return if (item.binding is ItemCryptoCurrencyBinding) {
                val holderSymbolName: String =
                    (item.binding as ItemCryptoCurrencyBinding).cryptoCurrency!!.englishName
                symbolName == holderSymbolName
            } else false
        }

        override fun describeTo(description: Description) {
            description.appendText("with task title: $symbolName")
        }
    }
}
