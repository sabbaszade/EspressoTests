package ir.part.app.signal.helpers

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Demonstrates how to set device in test friendly state to reduce test flakiness.
 */
@RunWith(AndroidJUnit4::class)
class DeviceSetupTest : BaseTest() {

    /**
     * Set of shell commands that should be run before test
     * which turn off System animations, increase Accessibility Touch & hold delay
     * and disable Virtual keyboard appearance.
     */

    @Test
    fun setDevicePreferences() {
        val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // System animation properties.
        uiDevice.executeShellCommand("settings put global animator_duration_scale 0.0")
        uiDevice.executeShellCommand("settings put global transition_animation_scale 0.0")
        uiDevice.executeShellCommand("settings put global window_animation_scale 0.0")

        // Touch & hold delay property.
        uiDevice.executeShellCommand("settings put secure long_press_timeout 1500")

        // Virtual keyboard appearance property.
        uiDevice.executeShellCommand("settings put secure show_ime_with_hard_keyboard 0")
    }
}