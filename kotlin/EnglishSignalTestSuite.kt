package ir.part.app.signal

import ir.part.app.signal.english.home.CryptoCurrencyScenarios
import ir.part.app.signal.english.home.EnglishSignal
import ir.part.app.signal.english.home.StockScenarios
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    EnglishSignal::class,
    CryptoCurrencyScenarios::class,
    StockScenarios::class
)
class EnglishSignalTestSuite