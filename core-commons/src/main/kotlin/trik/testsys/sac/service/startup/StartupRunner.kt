package trik.testsys.sac.service.startup

import kotlinx.coroutines.runBlocking

/**
 * Interface for services, which should be executed exactly when application is ready.
 *
 * @author Roman Shishkin
 * @since 1.1.0
 **/
interface StartupRunner {

    /**
     * Entry point to execute [StartupRunner] (suspend).
     *
     * @author Roman Shishkin
     * @since 1.1.0
     */
    suspend fun execute()

    /**
     * Entry point to execute [StartupRunner].
     *
     * @author Roman Shishkin
     * @since 1.1.0
     */
    fun executeBlocking() = runBlocking { execute() }
}