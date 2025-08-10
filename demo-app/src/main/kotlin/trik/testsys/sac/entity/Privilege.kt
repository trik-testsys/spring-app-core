package trik.testsys.sac.entity

import trik.testsys.sac.data.entity.user.UserPrivilege

enum class Privilege(override val code: String) : UserPrivilege  {

    ADMIN("ADMIN"),
    STUDENT("STUDENT"),
    VIEWER("VIEWER")
}