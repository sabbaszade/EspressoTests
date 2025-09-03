package ir.part.app.signal.utils

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.widget.Checkable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import com.google.android.material.tabs.TabLayout
import org.hamcrest.BaseMatcher
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.isA
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


object CustomViewMatchers {

    fun withDrawable(@DrawableRes id: Int) = object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("ImageView with drawable same as drawable with id $id")
        }

        override fun matchesSafely(view: View): Boolean {
            val context = view.context
            val expectedBitmap = context.getDrawable(id)!!.toBitmap()

            return view is ImageView && view.drawable.toBitmap().sameAs(expectedBitmap)
        }
    }

    fun selectTabAtPosition(tabIndex: Int): ViewAction {
        return object : ViewAction {
            override fun getDescription() = "with tab at index $tabIndex"

            override fun getConstraints() =
                allOf(isDisplayed(), isAssignableFrom(TabLayout::class.java))

            override fun perform(uiController: UiController, view: View) {
                val tabLayout = view as TabLayout
                val tabAtIndex: TabLayout.Tab = tabLayout.getTabAt(tabIndex)
                    ?: throw PerformException.Builder()
                        .withCause(Throwable("No tab at index $tabIndex"))
                        .build()

                tabAtIndex.select()
            }
        }
    }

    fun forceClick(): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return allOf(isClickable(), isEnabled(), isDisplayed())
            }

            override fun getDescription(): String {
                return "force click"
            }

            override fun perform(uiController: UiController, view: View) {
                view.performClick() // perform click without checking view coordinates.
                uiController.loopMainThreadUntilIdle()
            }
        }
    }

    fun setChecked(checked: Boolean): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): BaseMatcher<View?> {
                return object : BaseMatcher<View?>() {
                    override fun matches(item: Any): Boolean {
                        return isA(Checkable::class.java).matches(item)
                    }

                    override fun describeMismatch(item: Any, mismatchDescription: Description) {}
                    override fun describeTo(description: Description) {}
                }
            }

            override fun getDescription(): String? {
                return null
            }

            override fun perform(uiController: UiController, view: View) {
                val checkableView = view as Checkable
                checkableView.isChecked = checked
            }
        }
    }

    fun recyclerViewAtPositionOnView(
        position: Int,
        itemMatcher: Matcher<View?>,
        targetViewId: Int
    ): Matcher<View?>? {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has view id $itemMatcher at position $position")
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                val targetView = viewHolder!!.itemView.findViewById<View>(targetViewId)
                return itemMatcher.matches(targetView)
            }
        }
    }

    fun isConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}