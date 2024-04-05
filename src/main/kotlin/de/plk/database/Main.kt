package de.plk.database

import de.plk.database.model.AbstractModel
import de.plk.database.model.Group
import de.plk.database.model.Member
import de.plk.database.model.Rank
import de.plk.database.model.privot.MemberRankPivot
import java.util.*

/**
 * @author SoftwareBuilds
 * @since 18.02.2024 22:34
 * Copyright © 2024 | SoftwareBuilds | All rights reserved.
 */
fun main() {
    AbstractModel.getSchema(Member::class).create()
    AbstractModel.getSchema(MemberRankPivot::class).create()
    AbstractModel.getSchema(Rank::class).create()
    AbstractModel.getSchema(Group::class).create()

    Member().save()
}