package de.plk.database.sql.command

/**
 * Represents the sql command result.
 */
class CommandResult(

    /**
     * Represents that the command was an update.
     */
    val update: Boolean
) {

    /**
     * The sql command runs sucessfully.
     */
    var fullfilled: Boolean = false

    /**
     * The selected rows from a query command.
     */
    var resultSet: Map<Int, Map<String, Any>> = mapOf()

}