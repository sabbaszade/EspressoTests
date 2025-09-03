package ir.part.app.signal

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    PersianSignalTestSuite::class,
    EnglishSignalTestSuite::class
)
class SignalTestSuite