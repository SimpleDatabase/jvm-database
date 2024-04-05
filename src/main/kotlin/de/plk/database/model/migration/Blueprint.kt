package de.plk.database.model.migration

import de.plk.database.model.AbstractModel
import de.plk.database.model.meta.Column
import de.plk.database.model.meta.MetaReader
import de.plk.database.model.meta.Relation
import de.plk.database.model.meta.Table
import de.plk.database.model.relation.many.ToPivot
import de.plk.database.model.relation.one.BelongsTo
import de.plk.database.sql.command.Command
import de.plk.database.sql.command.CommandClosure
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
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
    val columns: List<Column>
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
            var primaryColumn = MetaReader.readAllPropertyAnnotations(it.relatedModel, Column::class).first()

            if (it.realtionType == ToPivot::class) {
                primaryColumn = MetaReader.readAllPropertyAnnotations(it.relatedModel, Column::class).filter {
                    it.columnName.equals(columns.find {
                        it.primary
                    }!!.columnName)
                }.first()
            }

            if (it.realtionType == BelongsTo::class || it.realtionType == ToPivot::class) {
                    relationLines = relationLines.plus(arrayOf(
                        "fk_${primaryColumn.columnName}",
                        primaryColumn.dataType.withSize(primaryColumn.size),
                        "FOREIGN KEY REFERENCES",
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
