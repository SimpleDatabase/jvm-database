package de.plk.database.sql.command

import de.plk.database.DatabasePool
import de.plk.database.DatabaseSource
import de.plk.database.sql.command.condition.Where
import java.sql.Connection
import java.sql.PreparedStatement

/**
 * Represents sql command patterns.
 */
enum class Command(

    /**
     * The command pattern.
     */
    val command: String
) {

    /**
     * The create command pattern.
     */
    CREATE("CREATE TABLE IF NOT EXISTS %s (%s);"),

    /**
     * The select command pattern.
     */
    SELECT("SELECT %s FROM %s"),

    /**
     * The update command pattern.
     */
    UPDATE("UPDATE %s SET %s"),

    /**
     * The delete command pattern.
     */
    DELETE("DELETE FROM %s"),

    /**
     * The insert command pattern.
     */
    INSERT("INSERT INTO %s VALUES %s"),

    /**
     * The alter command pattern.
     */
    ALTER("ALTER TABLE %s ADD %s");

    /**
     * Get the command string with replacements.
     *
     * @param replacements The command replacements.
     *
     * @return The replaced command string.
     */
    fun with(vararg replacements: String): String {
        return command.format(*replacements)
    }

    /**
     * Add a conidtion to command string.
     *
     * @param where Add a codition to the command.
     *
     * @return The command with a codition.
     */
    fun addWhere(where: Where): String {
        return command + where
    }

    /**
     * STATIC
     */
    companion object {

        /**
         * The command pool with sql connections.
         */
        private lateinit var pool: DatabasePool

        /**
         * Execute a sql command.
         *
         * @param command The command (SELECT, UPDATE, ...).
         * @param closure The closure with the command string replacements.
         *
         * @return The result of the command.
         */
        fun execute(command: Command, closure: CommandClosure): CommandResult {
            val result = CommandResult(command != SELECT)

            // Command string replacement.
            val commandReplacements: Array<String> = closure.apply()
            val sql = command.with(*commandReplacements)

            println(sql)

            return result

            val connection = pool.getConnection()

            val statement: PreparedStatement = connection.prepareStatement(sql)

            statement.use {
                when (command) {
                    CREATE, UPDATE, DELETE, INSERT, ALTER ->
                        result.fullfilled = statement.execute()

                    SELECT -> {
                        val callback = statement.executeQuery()

                        callback.use {
                            result.fullfilled = !callback.wasNull()

                            // remove last element, to get only the columns to get theirs values.
                            val commandReplacements = commandReplacements.slice(0..commandReplacements.size - 1)

                            // go to snack each row in table.
                            while (callback.next()) {
                                result.resultSet.plus(Pair(callback.row, mapOf()))

                                // add all the queried columns to the query map.
                                commandReplacements.forEach {
                                    result.resultSet[callback.row]?.plus(Pair(
                                        it, callback.getObject(it)
                                    ))
                                }
                            }
                        }
                    }
                }

                pool.releaseConnection(connection)

                return result
            }
        }
    }

}