package ir.part.app.signal.persian.home

import android.app.Instrumentation
import android.content.Intent
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListNotEmpty
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickBack
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem
import com.schibsted.spain.barista.interaction.BaristaListInteractions.scrollListToPosition
import com.schibsted.spain.barista.interaction.BaristaScrollInteractions.scrollTo
import com.schibsted.spain.barista.rule.flaky.AllowFlaky
import ir.part.app.signal.R.id.*
import ir.part.app.signal.R.string.*
import ir.part.app.signal.helpers.BaseTest
import ir.part.app.signal.helpers.CommonElements.showSnackBar
import ir.part.app.signal.helpers.CommonElements.waitForView
import ir.part.app.signal.helpers.ViewInteractions.assertWithTimeout
import ir.part.app.signal.helpers.testLinkReportWithId
import ir.part.app.signal.screens.AddRemoveBookmarkScreen
import ir.part.app.signal.screens.ShareCheckNewsScreen
import ir.part.app.signal.utils.ConditionWatchers.waitForElement
import ir.part.app.signal.utils.CustomViewMatchers.selectTabAtPosition
import ir.part.app.signal.utils.CustomViewMatchers.setChecked
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class StockTests : BaseTest() {

    @Before
    fun beforeTestsRun() {
        waitForView(rv_main_item, 10000)
        clickListItem(rv_main_item, 2)
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkMarketInStockTile() = testLinkReportWithId(testCase = 21872) {
        assertDisplayed(btn_market)
        assertDisplayed(stock_tools_tab)
        assertDisplayed(label_news)
        assertDisplayed(label_analysis)

        assertWithTimeout(12000) {
            assertDisplayed(sw_stock_market_state)

            assertDisplayed(
                tv_stock_market_state_information_title,
                label_stock_market_state
            )
            assertDisplayed(ll_stock_market_state_details_list)
            assertDisplayed(label_stock_index)
            assertDisplayed(label_stock_index_equal_weight)
            assertDisplayed("ارزش بازار (میلیارد تومان)")
            assertDisplayed("ارزش معاملات (میلیارد تومان)")
            assertDisplayed(label_stock_market_price_info)
        }
        assertDisplayed(tv_market_map_label, label_market_map)

        onView(withId(sw_stock_market_state))
            .perform(setChecked(true))

        val ultraStockIndex = onView(withText(label_ultra_stock_index))
        waitForElement(ultraStockIndex, 3000)

        onView(withId(sw_stock_market_state))
            .perform(setChecked(false))

        clickListItem(rv_stock_market_effective_list, 4)
        waitForView(cl_stock_details_chart, 9000)
        clickBack()

        clickListItem(rv_stock_market_effective_list, 5)
        waitForView(cl_stock_details_chart, 10000)
        clickBack()

        scrollListToPosition(rv_stock_market_effective_list, 3)
        assertDisplayedAtPosition(
            rv_stock_market_effective_list,
            3,
            label_stock_most_viewed
        )

        scrollListToPosition(rv_stock_market_effective_list, 7)
        clickListItem(rv_stock_market_effective_list, 7)

        assertDisplayed(label_fundamental)
        clickOn(label_fundamental)

        assertWithTimeout(11000) {
            assertDisplayed(tv_eps_title, label_eps_title)
            assertDisplayed(tv_esp_last_year, label_eps_last_year)
            assertDisplayed(tv_esp_current_year, label_eps_current_year)
            assertDisplayed(label_eps_warning)
            assertDisplayed(stock_eps_chart)
        }

        assertDisplayed(label_news)
        clickOn(label_news)
        assertDisplayed(label_announcements)
        clickOn(label_announcements)

        assertWithTimeout(7000) {
            assertDisplayed(rv_announcement)
            assertListNotEmpty(rv_announcement)
        }

        assertDisplayed(label_general)
        clickOn(label_general)
        clickOn(btn_chart_switch)
        clickOn(btn_comparison)
        clickOn(label_stock_index)

        assertWithTimeout(6000) {
            assertDisplayed(cv_comparison)
            assertDisplayed(label_chart_price_trend)
            assertDisplayed(comparison_chart)
        }

        clickOn(label_one_week)
        clickOn(label_three_month_numeric)
        clickOn(label_six_month_numeric)
        clickOn(label_one_year)
        clickOn(label_all)

        repeat(3) {
            clickBack()
        }

        val menuSearch = onView(withId(menu_search))
        waitForElement(menuSearch, 4000)

        clickOn(menu_search)
        writeTo(et_search, "شتوکا")

        val result = onView(withText("(توکا رنگ فولاد سپاهان)"))
        waitForElement(result, 10000)

        assertWithTimeout(5000) {
            clickListItem(rv_search, 0)
        }

        scrollTo(inc_stock_shematic_behaviour)
        scrollTo(inc_book_orders)
        scrollTo(tv_tse)
        assertDisplayed(tv_tse, label_tse)

        val expected = allOf(
            IntentMatchers.hasAction(Intent.ACTION_VIEW),
            IntentMatchers.hasData(("http://www.tsetmc.com/Loader.aspx?ParTree=151311&i=38555056423456635"))
        )

        Intents.intending(expected).respondWith(Instrumentation.ActivityResult(0, null))

        clickOn(tv_tse)

        Intents.intended(expected)

        assertWithTimeout(3000) {
            scrollTo(label_group_symbol)
            assertDisplayed(tv_stock_industry)

            scrollTo(tv_stock_details_chart_title)
        }

        clickOn(iv_stock_star)
        showSnackBar("به منتخب اضافه شد")

        clickOn(iv_stock_star)
        showSnackBar("از منتخب حذف شد")

        repeat(2) {
            clickBack()
        }

/*        assertWithTimeout(8000) {

            assertListNotEmpty(rv_stock_industries)

            repeat(4) {
                onView(instanceOf(SwipeRefreshLayout::class.java))
                    .perform(swipeUp())
            }
        }*/
    }

    @Test
    @AllowFlaky(attempts = 3)
    fun checkToolsTabExceptFilters() = testLinkReportWithId(testCase = 22807) {
        onView(withId(tl_stock)).perform(selectTabAtPosition(1))

        val rvStockTools = onView(withId(rv_stock_tools))
        waitForElement(rvStockTools, 10000)

        // codal
        assertDisplayedAtPosition(
            rv_stock_tools,
            1,
            tv_stock_category_title,
            "کُدال"
        )
        assertDisplayedAtPosition(
            rv_stock_tools,
            1,
            tv_stock_category_desc,
            label_desc_codal_filter
        )

        clickListItem(rv_stock_tools, 1)
        assertWithTimeout(10000) {
            assertDisplayedAtPosition(
                rv_codal_filter_list,
                0,
                label_schedule_payment_pnl_filter
            )
            assertDisplayedAtPosition(
                rv_codal_filter_list,
                6,
                label_capital_increase_filter
            )
            assertDisplayedAtPosition(
                rv_codal_filter_list,
                12,
                label_invitation_to_meetings_filter
            )
            assertDisplayedAtPosition(
                rv_codal_filter_list,
                18,
                label_disclosure_of_important_information_filter
            )
            assertDisplayedAtPosition(
                rv_codal_filter_list,
                24,
                label_monthly_performance_report_filter
            )
        }

        clickBack()

        // precedence
        waitForElement(rvStockTools, 4000)

        assertDisplayedAtPosition(
            rv_stock_tools,
            2,
            tv_stock_category_title,
            label_precedence
        )
        assertDisplayedAtPosition(
            rv_stock_tools,
            2,
            tv_stock_category_desc,
            desc_priority
        )

        clickListItem(rv_stock_tools, 2)
        assertWithTimeout(5000) {

            assertListNotEmpty(rv_stock_filter_list)
            assertDisplayedAtPosition(
                rv_stock_filter_list,
                0,
                label_stock_most_price_diff
            )
        }
        clickBack()

        // major shareholders changes
        waitForElement(rvStockTools, 4000)

        assertDisplayedAtPosition(
            rv_stock_tools,
            3,
            tv_stock_category_title,
            label_major_shareholders_changes
        )
        assertDisplayedAtPosition(
            rv_stock_tools,
            3,
            tv_stock_category_desc,
            desc_major_shareholders_changes
        )

        clickListItem(rv_stock_tools, 3)
        assertWithTimeout(5000) {
            assertDisplayedAtPosition(
                rv_major_shareholders_changes_filter_list,
                0,
                label_major_shareholders_changes_buy
            )
        }

        clickOn(label_sale)
        assertWithTimeout(5000) {
            onView(
                allOf(
                    withId(tv_major_shareholders_changes_top_header_title),
                    withText(label_major_shareholders_changes_sell),
                    isDisplayed()
                )
            )
                .check(matches(withText(label_major_shareholders_changes_sell)))
        }
    }

    @Test
    @AllowFlaky(attempts = 3)
    fun checkFiltersInStockTile() = testLinkReportWithId(testCase = 22809) {
        onView(withId(tl_stock)).perform(selectTabAtPosition(1))

        val rvStockTools = onView(withId(rv_stock_tools))
        waitForElement(rvStockTools, 4000)

        assertDisplayedAtPosition(
            rv_stock_tools,
            0,
            tv_stock_category_title,
            label_tab_stock_best
        )
        assertDisplayedAtPosition(
            rv_stock_tools,
            0,
            tv_stock_category_desc,
            desc_filters
        )

        clickListItem(rv_stock_tools, 0)
        // iquant
        assertDisplayedAtPosition(
            rv_stock_filter_category,
            0,
            tv_stock_category_title,
            label_advanced_filter_maker
        )
        assertDisplayedAtPosition(
            rv_stock_filter_category,
            0,
            tv_stock_category_desc,
            desc_iquant
        )

        // significant price changes
        assertWithTimeout(9000) {
            assertDisplayed(rv_stock_filter_category)
            clickListItem(rv_stock_filter_category, 1)

            assertDisplayedAtPosition(
                rv_stock_filter_list,
                0,
                label_stock_last_trade_thirty_days_percent_plus
            )
            assertDisplayedAtPosition(
                rv_stock_filter_list,
                6,
                label_stock_last_trade_ten_days_percent_plus
            )
            assertDisplayedAtPosition(
                rv_stock_filter_list,
                12,
                label_stock_market_best_change_plus
            )
            assertDisplayedAtPosition(
                rv_stock_filter_list,
                18,
                label_stock_price_gap_plus
            )
            assertDisplayedAtPosition(
                rv_stock_filter_list,
                24,
                label_stock_most_expensive
            )
        }

        clickOn(label_Reduction)

        val percentMinus =
            onView(withText(label_stock_last_trade_thirty_days_percent_minus))
        waitForElement(percentMinus, 4000)

        clickBack()

        // suspicious volumes
        val rvStockFilter = onView(withId(rv_stock_filter_category))
        waitForElement(rvStockFilter, 4000)

        assertDisplayed(rv_stock_filter_category)
        clickListItem(rv_stock_filter_category, 2)

        val mostBuy = onView(withText(label_stock_most_buy_queue_value_title))
        waitForElement(mostBuy, 4000)

        assertDisplayedAtPosition(
            rv_stock_filter_list,
            6,
            label_stock_most_sell_queue_value_title
        )
        assertDisplayedAtPosition(
            rv_stock_filter_list,
            12,
            label_stock_thirty_average_volume_potency
        )
        assertDisplayedAtPosition(
            rv_stock_filter_list,
            18,
            label_stock_ten_average_volume_potency
        )
        assertDisplayedAtPosition(
            rv_stock_filter_list,
            24,
            label_stock_market_best_trade_volume
        )
        assertDisplayedAtPosition(
            rv_stock_filter_list,
            30,
            label_stock_market_best_trade_value
        )

        clickBack()

        // smart money
        waitForElement(rvStockFilter, 4000)

        assertDisplayed(rv_stock_filter_category)
        clickListItem(rv_stock_filter_category, 3)

        val powerfulBuyer = onView(withText(label_stock_most_powerful_real_buyers))
        waitForElement(powerfulBuyer, 4000)

        assertDisplayedAtPosition(
            rv_stock_filter_list,
            6,
            label_stock_strongest_real_buy
        )
        assertDisplayedAtPosition(
            rv_stock_filter_list,
            12,
            label_most_real_money_in
        )
        assertDisplayedAtPosition(
            rv_stock_filter_list,
            18,
            label_stock_best_legal_buy
        )
        assertDisplayedAtPosition(
            rv_stock_filter_list,
            24,
            label_stock_best_real_buy
        )

        clickOn(label_sale)

        val realBuyer =
            onView(withText(label_stock_least_powerful_real_buyers))
        waitForElement(realBuyer, 4000)

        clickBack()

        // fundamental
        waitForElement(rvStockFilter, 4000)

        assertDisplayed(rv_stock_filter_category)
        clickListItem(rv_stock_filter_category, 4)

        val maxPe = onView(withText(label_stock_max_pe))
        waitForElement(maxPe, 4000)

        assertDisplayedAtPosition(
            rv_stock_filter_list,
            6,
            label_stock_min_pe
        )
        assertDisplayedAtPosition(
            rv_stock_filter_list,
            12,
            label_stock_max_eps
        )
        assertDisplayedAtPosition(
            rv_stock_filter_list,
            18,
            label_stock_min_eps
        )

        clickBack()

        // indicators
        waitForElement(rvStockFilter, 4000)

        assertDisplayed(rv_stock_filter_category)
        clickListItem(rv_stock_filter_category, 5)

        val minRsi = onView(withText(label_minimum_RSI))
        waitForElement(minRsi, 4000)

        assertDisplayedAtPosition(
            rv_stock_filter_list,
            6,
            label_maximum_RSI
        )
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkNewsAndAnalysis() = testLinkReportWithId(testCase = 23336) {
        ShareCheckNewsScreen().checkNewsAndShareIt(tl_stock, 2, 0)

        assertDisplayed(sm_topic)
        assertDisplayed(tv_topic, label_info_me_for_next_ipo)

        onView(withId(sm_topic))
            .perform(setChecked(true))
            .perform(setChecked(false))
            .perform(setChecked(true))

        assertWithTimeout(7000) {

            onView(withId(tl_stock)).perform(selectTabAtPosition(3))

            val rvAnalysis = onView(withId(rv_analysis))
            waitForElement(rvAnalysis, 7000)

            onView(instanceOf(SwipeRefreshLayout::class.java))
                .perform(swipeUp())

            assertListNotEmpty(rv_analysis)
        }
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkBookmarkingAStock() = testLinkReportWithId(testCase = 16265) {
        clickOn(menu_search)
        writeTo(et_search, "فملی")

        val stock = onView(withText("سهام"))
        waitForElement(stock, 15000)

        assertWithTimeout(15000) {
            assertDisplayedAtPosition(
                rv_search,
                0,
                tv_full_name,
                "(ملی صنایع مس ایران)"
            )
        }
        AddRemoveBookmarkScreen().addASymbolToBookmark(rv_search, 0, iv_star)

        repeat(3) {
            uiDevice.pressBack()
        }

        assertWithTimeout(12000) {
            clickOn(bookmarkFragment)

            assertDisplayedAtPosition(
                rv_bookmark,
                0,
                tv_stock_subject,
                "فملی"
            )
        }
        assertDisplayedAtPosition(
            rv_bookmark,
            0,
            tv_stock_full_name,
            "ملی صنایع مس ایران"
        )
        assertDisplayedAtPosition(
            rv_bookmark,
            0,
            tv_stock_unit,
            label_rials
        )

        AddRemoveBookmarkScreen().removeASymbolFromBookmark(rv_bookmark, 0, iv_stock_star)
    }

    @Test
    @AllowFlaky(attempts = 2)
    fun checkFilterDialog() = testLinkReportWithId(testCase = 28870) {
        onView(withId(tl_stock)).perform(selectTabAtPosition(1))

        assertWithTimeout(7000) {
            assertDisplayed(rv_stock_tools)
            clickListItem(rv_stock_tools, 0)

            assertDisplayed(rv_stock_filter_category)
            clickListItem(rv_stock_filter_category, 1)
        }

        clickOn(fab_filter)
        clickOn(btn_expandable_filter_close)
        clickOn(fab_filter)

        assertDisplayed(tv_filter_subject, label_filtering)
        assertDisplayed(iv_filter)
        assertDisplayed(btn_expandable_filter_close)
        assertDisplayed(btn_expandable_filter_apply)

        assertDisplayed("تمام بازارها")
        assertDisplayed(label_stock_market)
        assertDisplayed(label_stock_market_ultra)
        assertDisplayed("بازار پایه")

        clickOn(checkbox_parent)
        clickOn(btn_expandable_filter_apply)

        assertDisplayed(tv_empty_result, msg_empty_result)

        clickOn(fab_filter)
        clickOn(checkbox_parent)
        clickOn(btn_expandable_filter_apply)

        assertWithTimeout(9000) {
            assertDisplayed(rv_stock_filter_list)
            assertDisplayedAtPosition(
                rv_stock_filter_list,
                0,
                label_stock_last_trade_thirty_days_percent_plus
            )
            assertListNotEmpty(rv_stock_filter_list)
        }
    }
}