package de.plk.database.model.migration

import de.plk.database.DatabasePool
import de.plk.database.model.meta.Column
import de.plk.database.model.meta.Table
import de.plk.database.sql.command.Command
import de.plk.database.sql.command.CommandClosure

/**
 * Represens the schema of the table.
 */
class Blueprint(

    /**
     * The table information of table.
     */
    val table: Table,

    /**
     * The column information of table.
     */
    val columns: List<Column>
) {

    /**
     * Update the table on the database.
     *
     * @param pool The database pool with connections.
     */
    fun update(pool: DatabasePool) {

    }

    /**
     * Create the table on the database.
     *
     * @param pool The database pool with connections.
     */
    fun create(pool: DatabasePool) {
        Command.execute(Command.CREATE, CommandClosure {
            val columnMapping: StringBuilder = StringBuilder()

            if (columns.isEmpty())
                throw RuntimeException("Es muss mindestens eine Spalte angeben werden!")

            // add the schema row for each column
            columns.forEach {
                columnMapping.append(it.columnName)
                    .append(" ")
                    .append(it.dataType.withSize(it.size))
                    .append(" ")

                if (it.primary)
                    columnMapping.append("PRIMARY KEY")

                if (!it.nullable)
                    columnMapping.append("NOT NULL")

                columnMapping.append(",")
            }.also {
                // remove the last ',' from the command
                columnMapping.delete(columnMapping.length - 1, columnMapping.length)
            }

            return@CommandClosure arrayOf(table.tableName, columnMapping.toString())
        })
    }

}
