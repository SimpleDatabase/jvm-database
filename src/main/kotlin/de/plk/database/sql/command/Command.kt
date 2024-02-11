package de.plk.database.sql.command

import de.plk.database.DatabasePool
import de.plk.database.DatabaseSource
import de.plk.database.model.event.EventClosure
import java.sql.Connection
import java.sql.PreparedStatement

/**
 * @author SoftwareBuilds
 * @since 11.02.2024 22:22
 * Copyright © 2024 | SoftwareBuilds | All rights reserved.
 */
enum class Command(
    val command: String
) {

    CREATE("CREATE TABLE IF NOT EXISTS %s (%s);"),
    SELECT("SELECT %s FROM %s"),
    UPDATE("UPDATE %s SET %s"),
    DELETE("DELETE FROM %s"),
    INSERT("INSERT INTO %s VALUES %s"),
    ALTER("ALTER TABLE %s ADD %s");
    fun with(vararg replacements: String): String {
        return command.format(replacements)
    }

    companion object {
        private lateinit var pool: DatabasePool

        fun setupPool(poolSize: Byte, databaseSource: DatabaseSource) = DatabasePool(databaseSource, poolSize)

        fun execute(command: Command, closure: CommandClosure): CommandResult {
            val connection: Connection = pool.getConnection()
            val result = CommandResult(command != SELECT)

            val commandReplacements: Array<String> = closure.apply()
            val sql = command.with(*commandReplacements)

            val statement: PreparedStatement = connection.prepareStatement(sql)

            statement.use {
                when (command) {
                    CREATE, UPDATE, DELETE, INSERT, ALTER -> result.fullfilled = statement.execute()
                    SELECT -> {
                        val callback = statement.executeQuery()

                        callback.use {
                            result.fullfilled = !callback.wasNull()

                            val commandReplacements = commandReplacements.slice(0..commandReplacements.size - 1)

                            while (callback.next()) {
                                result.resultSet.plus(Pair(callback.row, mapOf()))

                                commandReplacements.forEach {
                                    result.resultSet[callback.row]?.plus(Pair(
                                        it, callback.getObject(it)
                                    ))
                                }
                            }
                        }
                    }
                }

                return result
            }
        }
    }

}