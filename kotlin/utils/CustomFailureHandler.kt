package ir.part.app.signal.utils


import android.content.Context
import android.view.View
import androidx.test.espresso.FailureHandler
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.base.DefaultFailureHandler
import org.hamcrest.Matcher


class CustomFailureHandler(targetContext: Context?) :
    FailureHandler {
    private val delegate: FailureHandler
    override fun handle(error: Throwable, viewMatcher: Matcher<View>) {
        try {
            delegate.handle(error, viewMatcher)
        } catch (e: NoMatchingViewException) {
            // For example done device dump, take screenshot, etc.
            throw e
        }
    }

    init {
        delegate = DefaultFailureHandler(targetContext)
    }
}