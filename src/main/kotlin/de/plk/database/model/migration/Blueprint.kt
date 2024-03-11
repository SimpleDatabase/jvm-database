package de.plk.database.model.migration

import de.plk.database.model.AbstractModel
import de.plk.database.model.meta.Column
import de.plk.database.model.meta.MetaReader
import de.plk.database.model.meta.Table
import de.plk.database.model.relation.Relation
import de.plk.database.model.relation.many.HasMany
import de.plk.database.sql.command.Command
import de.plk.database.sql.command.CommandClosure

/**
 * Represens the schema of the table.
 */
class Blueprint<M : AbstractModel<M>, O : AbstractModel<O>>(

    /**
     * The table information of table.
     */
    val table: Table,

    /**
     * The column information of table.
     */
    val columns: List<Column>,

    val relations: List<Relation<M, O>>
) {

    private fun addRelations(): Array<String> {
        var relationLines = arrayOf<String>()
        relations.forEach {
            val relatedTableInformation = MetaReader.readClassAnnotation(it.related::class, Table::class)
            val primaryColumn = MetaReader.readAllPropertyAnnotations(it.related::class, Column::class).first()

            when(it) {
                is HasMany -> {
                    relationLines = relationLines.plus(arrayOf(
                        primaryColumn.columnName,
                        primaryColumn.dataType.withSize(primaryColumn.size),
                        "FOREIGN KEY REFERENCES",
                        relatedTableInformation.tableName,
                        "(${primaryColumn.columnName})"
                    ).joinToString(" "))
                }
            }
        }

        return relationLines
    }

    /**
     * Create the table on the database.
     */
    fun create() {
        Command.execute(Command.CREATE, CommandClosure {
            if (columns.isEmpty())
                throw RuntimeException("Es muss mindestens eine Spalte angeben werden!")

            var columnAttributes = arrayOf<String>()

            // add the schema row for each column
            columns.forEach {

                var columnAttribut = arrayOf(
                    it.columnName,
                    it.dataType.withSize(it.size)
                )

                if (it.primary)
                    columnAttribut = columnAttribut.plus("PRIMARY KEY")

                if (!it.nullable)
                    columnAttribut = columnAttribut.plus("NOT NULL")

                columnAttributes = columnAttributes.plus(columnAttribut.joinToString(" "))
            }

            columnAttributes = columnAttributes.plus(addRelations())

            return@CommandClosure arrayOf(table.tableName, columnAttributes.joinToString(","))
        })
    }

}
