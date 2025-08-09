package trik.testsys.sac.utils.enums

/**
 * Marker for enums that are stored in DB via a short stable key.
 *
 * @author Roman Shishkin
 * @since %CURRENT_VERSION%
 */
interface Enum {

    val dbkey: String
}


