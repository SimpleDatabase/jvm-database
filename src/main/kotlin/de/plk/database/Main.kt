package de.plk.database

import de.plk.database.model.AbstractModel
import de.plk.database.model.Group
import de.plk.database.model.Member
import de.plk.database.model.Rank
import de.plk.database.model.privot.MemberRankPivot
import de.plk.database.sql.build.QueryBuilder
import de.plk.database.sql.command.Command
import java.util.*

/**
 * @author SoftwareBuilds
 * @since 18.02.2024 22:34
 * Copyright © 2024 | SoftwareBuilds | All rights reserved.
 */
fun main() {
    val properties = Properties()

    properties.setProperty("hostname", "localhost")
    properties.setProperty("port", "3306")
    properties.setProperty("database", "jvm_database")
    properties.setProperty("username", "root")
    properties.setProperty("password", "xyz")

    Command.pool = DatabasePool(
        DatabaseSource(
            DatabaseType.MARIADB,
            properties
        )
    )

    AbstractModel.getSchema(Group::class).create()
    AbstractModel.getSchema(Member::class).create()
    AbstractModel.getSchema(Rank::class).create()
    AbstractModel.getSchema(MemberRankPivot::class).create()

    val model = Group(groupId = 0)
    model.groupName = "Test2"
    model.save()
    println(model.member().related.memberId)
}