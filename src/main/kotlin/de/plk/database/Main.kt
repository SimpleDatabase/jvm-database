package de.plk.database

import de.plk.database.model.Group
import de.plk.database.model.Member
import java.util.*

/**
 * @author SoftwareBuilds
 * @since 18.02.2024 22:34
 * Copyright © 2024 | SoftwareBuilds | All rights reserved.
 */
fun main() {
    val member = Member()
    member.getSchema().create()
}