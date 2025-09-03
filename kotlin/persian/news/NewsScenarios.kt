package ir.part.app.signal.persian.news

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.schibsted.spain.barista.assertion.BaristaImageViewAssertions.assertHasDrawable
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListNotEmpty
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItemChild
import com.schibsted.spain.barista.interaction.BaristaScrollInteractions.scrollTo
import com.schibsted.spain.barista.rule.flaky.AllowFlaky
import ir.part.app.signal.R
import ir.part.app.signal.R.id.*
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.helpers.CommonElements.waitForView
import ir.part.app.signal.helpers.ViewInteractions.assertWithTimeout
import ir.part.app.signal.helpers.testLinkReportWithId
import ir.part.app.signal.utils.ConditionWatchers.waitForElement
import ir.part.app.signal.utils.CustomViewMatchers.forceClick
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class NewsScenarios : BaseTest() {

    @Test
    @AllowFlaky(attempts = 2)
    fun checkNewsFragment() = testLinkReportWithId(testCase = 29345) {
        waitForView(rv_main_item, 10000)
        waitForView(newsFragment, 9000)

        val news = onView(withId(newsFragment))
        news.perform(forceClick())

        val categories = listOf(
            "اخبار برگزیده",
            "بورس",
            "طلا و ارز",
            "ارز دیجیتال",
            "سیاسی",
            "بین\u200Cالملل",
            "اقتصاد کلان",
            "خودرو",
            "کالا و انرژی",
            "مسکن و عمران"
        )
        waitForView("بورس", 9000)
        categories.forEach {
            scrollTo(it)
            clickOn(it)
            assertListNotEmpty(rv_news)
        }

        assertWithTimeout(8000) {
            assertDisplayed((rv_news))
            clickListItemChild(rv_news, 2, iv_ai_news)
        }

        val ivPodcast = onView(withId(iv_podcast_cover))
        waitForElement(ivPodcast, 6000)

        assertDisplayed((iv_podcast_cover))
        assertHasDrawable(exo_ffwd, R.drawable.ic_ffwd)
        assertHasDrawable(exo_rew, R.drawable.ic_rew)
    }
}