package de.plk.database

import de.plk.database.model.AbstractModel
import de.plk.database.model.Group
import de.plk.database.model.Member
import de.plk.database.model.Rank
import de.plk.database.model.Operand
import de.plk.database.model.privot.MemberRankPivot
import de.plk.database.sql.command.Command
import java.util.*
import kotlin.system.measureTimeMillis

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
            DatabaseType.SQLITE,
            properties
        )
    )

    AbstractModel.getSchema(Group::class).create()
    AbstractModel.getSchema(Member::class).create()
    AbstractModel.getSchema(Rank::class).create()
    AbstractModel.getSchema(MemberRankPivot::class).create()

    val time = measureTimeMillis {
        val group = Group(id = 1)
        group.groupName = "Admin"
        group.kuerzel = "ADM"
        group.save()

        val member = Member(id = 1)
        member.groups_id = group.id
        member.name = "Phil"
        member.save()

        println(member.name)
        println(member.group().getRelated()?.groupName)
    }

    println("IT takes: $time ms")

}