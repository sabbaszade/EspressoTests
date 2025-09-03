package ir.part.app.signal.helpers

import ir.part.app.signal.helpers.BaseTest.Companion.BUILD
import ir.part.app.signal.helpers.BaseTest.Companion.TEST_PLAN
import junit.framework.AssertionFailedError
import testlink.api.java.client.TestLinkAPIClient
import testlink.api.java.client.TestLinkAPIException
import testlink.api.java.client.TestLinkAPIResults

@Throws(TestLinkAPIException::class)
fun reportTestResultUsingName(
    TestPlanName: String?,
    TestcaseName: String?,
    BuildName: String?,
    Notes: String?,
    Result: String?
) {
    val DEVKEY = "40d89235db4b191d340d0fbf258ecdf0"
    val URL = "http://192.168.5.118/testlink/lib/api/xmlrpc/v1/xmlrpc.php"
    val api = TestLinkAPIClient(DEVKEY, URL)
    api.reportTestCaseResult(
        "سیگنال",
        TestPlanName,
        TestcaseName,
        BuildName,
        Notes,
        Result
    )
}

@Throws(TestLinkAPIException::class)
fun reportTestResultUsingId(
    TestPlanId: Int?,
    TestcaseId: Int?,
    BuildId: Int?,
    Notes: String?,
    Result: String?
) {
    val DEVKEY = "40d89235db4b191d340d0fbf258ecdf0"
    val URL = "http://192.168.5.118/testlink/lib/api/xmlrpc/v1/xmlrpc.php"
    val api = TestLinkAPIClient(DEVKEY, URL)
    api.reportTestCaseResult(
        TestPlanId,
        TestcaseId,
        BuildId,
        Notes,
        Result
    )
}

fun testLinkReportWithId(
    testPlan: Int = TEST_PLAN,
    testCase: Int,
    build: Int = BUILD,
    block: () -> Any
) {
    var result = TestLinkAPIResults.TEST_PASSED
    var notes = "Executed successfully"
    try {
        block()
    } catch (e: Exception) {
        result = TestLinkAPIResults.TEST_FAILED
        notes = "Execution failed\n" + e.message
        throw AssertionFailedError(e.message)
    } finally {
        reportTestResultUsingId(testPlan, testCase, build, notes, result)
    }
}