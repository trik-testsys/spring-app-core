package trik.testsys.sac.controller.response

import kotlin.reflect.full.declaredMemberProperties


/**
 * @author Roman Shishkin
 * @since %CURRENT_VERSION%
 */
interface ResponseData {

    companion object {

        /**
         * Reflectively converts a [ResponseData] instance to a map of property names to values.
         *
         * @author Roman Shishkin
         * @since %CURRENT_VERSION%
         */
        fun ResponseData.toMap(): Map<String, Any?> {
            val properties = this::class.declaredMemberProperties
            return properties.associate { it.name to it.getter.call(this) }
        }
    }
}