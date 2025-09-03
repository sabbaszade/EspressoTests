package ir.part.app.signal.helpers

object ViewInteractions {

    @JvmStatic
    fun assertWithTimeout(
        timeoutLimit: Int = 500,
        assertion: () -> Unit
    ) {
        val watchInterval = 250L

        var hasMetCondition = false
        var elapsedTime = 0L

        do {
            try {
                assertion()
                hasMetCondition = true
            } catch (e: Exception) {
                elapsedTime += watchInterval
                Thread.sleep(watchInterval)
            }
            if (elapsedTime >= timeoutLimit) {
                break
            }
        } while (!hasMetCondition)

        if (!hasMetCondition) {
            throw Exception(
                assertion.toString() + " - took more than " + timeoutLimit / 1000 + " seconds. Test stopped."
            )
        }
    }
}