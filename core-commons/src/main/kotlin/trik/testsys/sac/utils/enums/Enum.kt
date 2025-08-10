package trik.testsys.sac.utils.enums

/**
 * Marker for enums that are stored in DB via a short stable key.
 *
 * @author Roman Shishkin
 * @since %CURRENT_VERSION%
 */
interface Enum {

    /**
     * Short, stable key stored in DB instead of the enum name.
     *
     * @author Roman Shishkin
     * @since %CURRENT_VERSION%
     */
    val dbkey: String
}


