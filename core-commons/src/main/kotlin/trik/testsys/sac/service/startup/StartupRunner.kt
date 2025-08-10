package trik.testsys.sac.service.startup

import kotlinx.coroutines.runBlocking

/**
 * Interface for services, which should be executed exactly when application is ready.
 *
 * @author Roman Shishkin
 * @since 1.1.0
 **/
interface StartupRunner {

    fun executeBlocking() = runBlocking { execute() }

    suspend fun execute()
}