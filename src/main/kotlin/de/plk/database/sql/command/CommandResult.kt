package de.plk.database.sql.command

/**
 * Represents the sql command result.
 */
class CommandResult {

    /**
     * The sql command runs sucessfully.
     */
    var fullfilled: Boolean = false

    /**
     * The selected rows from a query command.
     */
    var resultSet: MutableMap<Int, MutableMap<String, Any>> = mutableMapOf()

}