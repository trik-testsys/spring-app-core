package trik.testsys.sac.utils.enums

/**
 * Marker for enums that are stored in DB via a short stable key.
 */
interface Enum {
    val dbkey: String
}


