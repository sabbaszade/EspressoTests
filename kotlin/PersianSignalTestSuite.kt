package ir.part.app.signal

import ir.part.app.signal.helpers.DeviceSetupTest
import ir.part.app.signal.persian.analysis.AnalysisScenarios
import ir.part.app.signal.persian.bookmark.BookmarkScenarios
import ir.part.app.signal.persian.home.*
import ir.part.app.signal.persian.home.investment.InvestmentTests
import ir.part.app.signal.persian.intro.IntroTests
import ir.part.app.signal.persian.menu.ProfileTests
import ir.part.app.signal.persian.menu.aboutus.AboutUsScenarios
import ir.part.app.signal.persian.menu.calculator.BankLoanTest
import ir.part.app.signal.persian.menu.calculator.CapitalIncreaseTest
import ir.part.app.signal.persian.menu.calculator.TechnicalCalculatorTest
import ir.part.app.signal.persian.menu.callcenter.CallCenterScenarios
import ir.part.app.signal.persian.menu.login.LoginTests
import ir.part.app.signal.persian.menu.login.SignUpTest
import ir.part.app.signal.persian.news.NewsScenarios
import ir.part.app.signal.persian.programs.ProgramsScenarios
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    DeviceSetupTest::class,
    IntroTests::class,
    CryptoCurrencyTests::class,
    AutomobileTests::class,
    BankTests::class,
    ForexTests::class,
    CommodityTests::class,
    FundsTests::class,
    InvestmentTests::class,
    GoldAndCurrencyTests::class,
    InsuranceTests::class,
    AboutUsScenarios::class,
    RealEstateTests::class,
    StockTests::class,
    AnalysisScenarios::class,
    BookmarkScenarios::class,
    BankLoanTest::class,
    CapitalIncreaseTest::class,
    TechnicalCalculatorTest::class,
    LoginTests::class,
    ProfileTests::class,
    SignUpTest::class,
    NewsScenarios::class,
    ProgramsScenarios::class,
    CallCenterScenarios::class,
    BondTests::class
)
class PersianSignalTestSuite