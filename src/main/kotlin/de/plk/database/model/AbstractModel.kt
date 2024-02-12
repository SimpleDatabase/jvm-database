package de.plk.database.model

import de.plk.database.action.companion.ModelEventType
import de.plk.database.action.operation.ModelOperation
import de.plk.database.model.event.EventClosure
import de.plk.database.model.meta.Column
import de.plk.database.model.meta.MetaReader
import de.plk.database.model.meta.Table
import de.plk.database.model.migration.Blueprint
import de.plk.database.model.relation.Relation
import de.plk.database.model.relation.many.BelongsToMany
import de.plk.database.model.relation.many.HasMany
import de.plk.database.model.relation.one.BelongsTo
import de.plk.database.model.relation.one.HasOne
import de.plk.database.model.scope.GlobalScope
import de.plk.database.sql.QueryBuilder
import de.plk.database.sql.command.Command
import de.plk.database.sql.command.CommandClosure
import de.plk.database.sql.command.condition.Where
import kotlin.reflect.KClass

/**
 * Defines that any subclass is a database model.
 */
abstract class AbstractModel : QueryBuilder, ModelOperation {

    /**
     * The registered events for the model.
     */
    private val eventMap: Map<ModelEventType, List<EventClosure<AbstractModel>>> = mutableMapOf()

    /**
     * The registered scopes for this model.
     */
    private val globalScopes: List<GlobalScope<AbstractModel>> = listOf()

    /**
     * True if model ist not created/ saved yet.
     */
    private var created = false

    /**
     * The table information of the model.
     */
    private val table: Table = MetaReader.readClassAnnotation(this::class, Table::class)

    /**
     * The columns information of the model.
     */
    private val columns: List<Column> = MetaReader.readClassAnnotations(this::class, Column::class)

    /**
     * The relations of the model.
     */
    private val relations: List<Relation> = listOf()

    /**
     * The scopes of the models.
     */
    private val wheres: List<Where> = listOf()

    /**
     * The boot function of model.
     */
    abstract fun boot()

    /**
     * Call/add an event for the model.
     *
     * @param eventType The type of the event.
     * @param callback  The closure (event statements) to run.
     */
    protected fun event(eventType: ModelEventType, callback: EventClosure<out AbstractModel>) {
        if (!eventMap.containsKey(eventType)) {
            eventMap.plus(Pair(eventType, listOf()))
        }

        eventMap[eventType]!!.plus(callback)
    }

    /**
     * Dispatch the registered events for the given type.
     *
     * @param eventType The type to dispatch the events for.
     */
    private fun applyEvent(eventType: ModelEventType) {
        eventMap[eventType]?.forEach { it.apply(this) }
    }

    /**
     * Get the table schema of the model.
     */
    private fun getSchema(): Blueprint {
        return Blueprint(table, columns)
    }

    /**
     * Save the model.
     */
    override fun save() {
        when (created) {

            // Creating the model as row in the database.
            false -> {
                applyEvent(ModelEventType.CREATING)

                Command.execute(Command.INSERT, CommandClosure {
                    return@CommandClosure arrayOf(table.tableName)
                })

                applyEvent(ModelEventType.CREATED)
            }

            // Saving the model to the given row in the database.
            true -> {
                applyEvent(ModelEventType.SAVING)

                Command.execute(Command.UPDATE, CommandClosure {
                    val updateMapping = StringBuilder()

                    columns.forEach {
                        val data = MetaReader.readValue(this, Column::class, it.columnName)
                         updateMapping.append(it.columnName)
                            .append("=")
                            .append(data.get(this as Nothing)) // TODO
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

    /**
     * Delete the model.
     */
    override fun delete() {
        applyEvent(ModelEventType.DELETING)

        Command.execute(Command.DELETE, CommandClosure {
            return@CommandClosure arrayOf(table.tableName)
        })

        applyEvent(ModelEventType.DELETED)
    }

    /**
     * Add a global scope to the model.
     *
     * @param scope The global scope.
     */
    fun <M : AbstractModel> addGlobalScope(scope: GlobalScope<M>) {
        globalScopes.plus(scope)
    }

    /**
     * {@inheritDoc}
     */
    override fun where(column: String, needle: Any, operand: QueryBuilder.Operand): QueryBuilder {
        wheres.plus(Where(column, needle, operand))
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun orWhere(column: String, needle: Any, operand: QueryBuilder.Operand): QueryBuilder {
        wheres.plus(Where(column, needle, operand, Where.Type.OR))
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun andWhere(column: String, needle: Any, operand: QueryBuilder.Operand): QueryBuilder {
        wheres.plus(Where(column, needle, operand, Where.Type.AND))
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun <M : AbstractModel> belongsToMany(model: KClass<M>): BelongsToMany {
        return BelongsToMany(this).also {
            relations.plus(it)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun <M : AbstractModel> belongsTo(model: KClass<M>): BelongsTo {
        return BelongsTo(this).also {
            relations.plus(it)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun <M : AbstractModel> hasMany(model: KClass<M>): HasMany {
        return HasMany(this).also {
            relations.plus(it)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun <M : AbstractModel> hasOne(model: KClass<M>): HasOne {
        return HasOne(this).also {
            relations.plus(it)
        }
    }

}