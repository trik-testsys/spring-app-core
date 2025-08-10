package trik.testsys.sac.privilege

import trik.testsys.sac.data.entity.user.UserPrivilege

/**
 * Demo application's concrete privileges.
 */
enum class DemoUserPrivilege(override val code: String) : UserPrivilege {
    VIEW_DEMO("VIEW_DEMO"),
    EDIT_DEMO("EDIT_DEMO"),
    ADMIN("ADMIN")
}


