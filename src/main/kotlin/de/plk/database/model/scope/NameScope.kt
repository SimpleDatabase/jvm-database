package de.plk.database.model.scope

import de.plk.database.model.Member
import de.plk.database.sql.build.QueryBuilder

/**
 * Represents a name scope.
 */
class NameScope : GlobalScope<Member> {

    override fun scope(model: Member) {
        model.where("name", "%h%", QueryBuilder.Operand.LIKE)
    }

}