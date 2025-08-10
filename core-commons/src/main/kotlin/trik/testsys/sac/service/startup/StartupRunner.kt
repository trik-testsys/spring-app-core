package trik.testsys.sac.service.startup

import kotlinx.coroutines.runBlocking

/**
 * Interface for services, which should be executed exactly when application is ready.
 *
 * @author Roman Shishkin
 * @since %CURRENT_VERSION%
 **/
interface StartupRunner {

    fun executeBlocking() = runBlocking { execute() }

    suspend fun execute()
}