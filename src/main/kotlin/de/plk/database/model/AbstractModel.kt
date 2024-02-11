package de.plk.database.model

import de.plk.database.action.companion.ModelEventType
import de.plk.database.action.operation.ModelOperation
import de.plk.database.model.event.EventClosure
import de.plk.database.model.meta.Column
import de.plk.database.model.meta.MetaReader
import de.plk.database.model.meta.Table
import de.plk.database.model.relation.Relation
import de.plk.database.model.scope.Scope
import de.plk.database.sql.QueryBuilder
import de.plk.database.sql.command.Command
import de.plk.database.sql.command.CommandClosure
import kotlin.reflect.full.declaredMembers
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedType

/**
 * Defines that any subclass is a database model.
 */
abstract class AbstractModel : ModelOperation {

    protected val builder: QueryBuilder? = null

    private val eventMap: Map<ModelEventType, List<EventClosure<AbstractModel>>> = mutableMapOf()

    private val globalScopes: List<Scope<AbstractModel>> = listOf()

    private var created = false

    private val metaReader: MetaReader = MetaReader

    private val relations: List<Relation> = listOf()

    abstract fun boot()

    protected fun event(eventType: ModelEventType, callback: EventClosure<out AbstractModel>) {
        if (!eventMap.containsKey(eventType)) {
            eventMap.plus(Pair(eventType, listOf()))
        }

        eventMap[eventType]!!.plus(callback)
    }

    private fun applyEvent(eventType: ModelEventType) {
        eventMap[eventType]?.forEach { it.apply(this) }
    }

    override fun save() {
        when (created) {
            false -> {
                applyEvent(ModelEventType.CREATING)

                Command.execute(Command.UPDATE, CommandClosure {
                    val table: Table = metaReader.readClassAnnotation(this::class, Table::class)
                    return@CommandClosure arrayOf(table.tableName)
                })

                applyEvent(ModelEventType.CREATED)
            }

            true -> {
                applyEvent(ModelEventType.SAVING)

                Command.execute(Command.UPDATE, CommandClosure {
                    val table: Table = metaReader.readClassAnnotation(this::class, Table::class)
                    val columns: List<Column> = metaReader.readFieldAnnotations(this::class, Column::class)

                    val updateMapping = StringBuilder()
                    columns.forEach {
                        val data = metaReader.readValue(this, Column::class, it.columnName)
                        val append = updateMapping.append(it.columnName)
                            .append("=")
                            .append(data.getValue(this, data.getter.property))
                            .append(",")
                    }.also {
                        updateMapping.delete(updateMapping.length - 1, updateMapping.length)
                    }

                    return@CommandClosure arrayOf(table.tableName, updateMapping.toString())
                })


                applyEvent(ModelEventType.SAVED)
            }
        }
    }

    override fun delete() {
        applyEvent(ModelEventType.DELETING)

        Command.execute(Command.DELETE, CommandClosure {
            val table: Table = metaReader.readClassAnnotation(this::class, Table::class)
            return@CommandClosure arrayOf(table.tableName)
        })

        applyEvent(ModelEventType.DELETED)
    }

    fun <M : AbstractModel> addGlobalScope(scope: Scope<M>) {
        globalScopes.plus(scope)
    }

    protected fun addForeign() {
        val table = metaReader.readClassAnnotation(this::class, Table::class)

        Command.execute(Command.ALTER, CommandClosure {
            val relations: List<Column> = metaReader.readFieldAnnotations(this::class, Column::class).filter {
                !it.foreign.foreignKey.isEmpty()
            }

            val foreignBuilder = StringBuilder()
            relations.forEach {
                val foreignTable: Table = metaReader.readClassAnnotation(it.foreign.modelClass, Table::class)

                foreignBuilder.append("FOREIGN KEY (")
                    .append(it.columnName)
                    .append(")")
                    .append(" REFERENCES ")
                    .append(foreignTable.tableName)
                    .append("(")
                    .append(it.columnName)
                    .append(")")
                    .append(",")
            }.also {
                foreignBuilder.delete(foreignBuilder.length - 1, foreignBuilder.length)
            }

            return@CommandClosure arrayOf(table.tableName, foreignBuilder.toString())
        })
    }

    protected fun createTable() {
        Command.execute(Command.CREATE, CommandClosure {
            val table: Table = metaReader.readClassAnnotation(this::class, Table::class)
            val columns: List<Column> = metaReader.readFieldAnnotations(this::class, Column::class)

            val columnMapping: StringBuilder = StringBuilder()

            if (columns.isEmpty())
                throw RuntimeException("Es muss mindestens eine Spalte angeben werden!")

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
                columnMapping.delete(columnMapping.length - 1, columnMapping.length)
            }

            return@CommandClosure arrayOf(table.tableName, columnMapping.toString())
        })
    }

}