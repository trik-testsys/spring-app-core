package trik.testsys.sac.utils.enums

/**
 * Marker for enums that are stored in DB via a short stable key.
 *
 * @author Roman Shishkin
 * @since 1.1.0
 */
interface Enum {

    /**
     * Short, stable key stored in DB instead of the enum name.
     *
     * @author Roman Shishkin
     * @since 1.1.0
     */
    val dbkey: String
}


