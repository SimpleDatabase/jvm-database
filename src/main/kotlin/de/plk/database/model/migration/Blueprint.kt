package de.plk.database.model.migration

import de.plk.database.model.AbstractModel
import de.plk.database.model.meta.Column
import de.plk.database.model.meta.MetaReader
import de.plk.database.model.meta.Relation
import de.plk.database.model.meta.Table
import de.plk.database.model.relation.many.BelongsToMany
import de.plk.database.model.relation.one.BelongsTo
import de.plk.database.sql.command.Command
import de.plk.database.sql.command.CommandClosure
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotations
import kotlin.reflect.full.memberFunctions

/**
 * Represens the schema of the table.
 */
class Blueprint<M : AbstractModel<M>>(

    val clazz: KClass<out M>,

    /**
     * The table information of table.
     */
    val table: Table,

    /**
     * The column information of table.
     */
    var columns: List<Column>
) {

    private var relationLines = arrayOf<String>()

    fun addRelations(): Array<String> {
        if (relationLines.isNotEmpty()) return relationLines

        val relations = clazz.memberFunctions.filter {
            it.findAnnotations(Relation::class).isNotEmpty()
        }.map {
            it.findAnnotations(Relation::class).first()
        }

        relations.forEach {
            val relatedTableInformation = MetaReader.readClassAnnotation(it.relatedModel, Table::class)
            var primaryColumn = MetaReader.readAllPropertyAnnotations(it.relatedModel, Column::class).first {
                it.primary
            }

            if (it.relationType == BelongsTo::class) {
                relationLines = relationLines.plus(arrayOf(
                    primaryColumn.columnName,
                    primaryColumn.dataType.withSize(primaryColumn.size)
                ).joinToString(" "))
            }

            if (it.relationType == BelongsTo::class || (it.relationType == BelongsToMany::class && !it.pivot)) {
                relationLines = relationLines.plus(arrayOf(
                    "CONSTRAINT",
                    "fk_${primaryColumn.columnName}",
                    "FOREIGN KEY (${primaryColumn.columnName}) REFERENCES",
                    relatedTableInformation.tableName,
                    "(${primaryColumn.columnName})"
                ).joinToString(" "))
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

            columns = columns.sortedByDescending { it.primary }

            var columnAttributes = arrayOf<String>()

            // Add the schema row for each column.
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
