package ir.part.app.signal.helpers

import android.graphics.Bitmap
import androidx.test.runner.screenshot.Screenshot
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.io.IOException

/**
 * The rule which captures screenshot on test failure.
 */
class ScreenshotWatcher : TestWatcher() {
    override fun failed(e: Throwable, desc: Description) {
        try {
            captureScreenshot(desc.methodName)
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
    }

    /**
     * Takes screenshot and saves it in sdcard/Pictures/screenshots location.
     * Or in /storage/emulated/0/Pictures/screenshots/-.png
     * Each screenshot will have name containing unique uuid.
     *
     * @param name screenshot name
     * @throws IOException if there is an IOException
     */
    @Throws(IOException::class)
    private fun captureScreenshot(name: String) {
        val capture = Screenshot.capture()
        capture.format = Bitmap.CompressFormat.PNG
        capture.name = name
        capture.process()
    }
}