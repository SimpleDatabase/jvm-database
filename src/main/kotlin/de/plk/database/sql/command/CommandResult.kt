package de.plk.database.sql.command

/**
 * @author SoftwareBuilds
 * @since 11.02.2024 22:49
 * Copyright © 2024 | SoftwareBuilds | All rights reserved.
 */
class CommandResult(
    val update: Boolean
) {

    var fullfilled: Boolean = false

    var resultSet: Map<Int, Map<String, Any>> = mapOf()

}