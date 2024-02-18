package de.plk.database.model.scope

import de.plk.database.model.Member

/**
 * Represents a name scope.
 */
class NameScope : GlobalScope<Member> {

    override fun scope(model: Member) {
        model.where("name", "Phil")
    }

}