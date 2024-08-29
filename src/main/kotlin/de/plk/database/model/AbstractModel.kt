package de.plk.database.model

import de.plk.database.action.companion.ModelEventType
import de.plk.database.action.operation.ModelOperation
import de.plk.database.model.event.EventClosure
import de.plk.database.model.meta.Column
import de.plk.database.model.meta.MetaReader
import de.plk.database.model.meta.Table
import de.plk.database.model.migration.Blueprint
import de.plk.database.model.relation.many.BelongsToMany
import de.plk.database.model.relation.many.HasMany
import de.plk.database.model.relation.one.BelongsTo
import de.plk.database.model.relation.one.HasOne
import de.plk.database.model.scope.GlobalScope
import de.plk.database.sql.build.QueryBuilder
import de.plk.database.sql.command.Command
import de.plk.database.sql.command.CommandClosure
import de.plk.database.sql.command.condition.Where
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.primaryConstructor

/**
 * Defines that any subclass is a database model.
 */
open abstract class AbstractModel<M : AbstractModel<M>> : QueryBuilder<M>, ModelOperation {

    /**
     * The sub model instance.
     */
    lateinit var model: M

    /**
     * The registered events for the model.
     */
    private val eventMap = mutableMapOf<ModelEventType, MutableList<EventClosure<M>>>()

    /**
     * The registered scopes for this model.
     */
    protected val globalScopes= mutableListOf<GlobalScope<M>>()

    /**
     * True if model ist not created/ saved yet.
     */
    protected var created = false

    /**
     * The table information of the model.
     */
    protected lateinit var table: Table

    /**
     * The columns information of the model.
     */
    protected lateinit var columns: List<Column>

    /**
     * The scopes of the models.
     */
    private val wheres = mutableListOf<Where>()

    /**
     * The boot function of model.
     */
    open fun boot(model: M) {
        this.model = model

        table = MetaReader.readClassAnnotation(model::class, Table::class)
        columns = MetaReader.readAllPropertyAnnotations(model::class, Column::class)
    }

    /**
     * Call/add an event for the model.
     *
     * @param eventType The type of the event.
     * @param callback  The closure (event statements) to run.
     */
    protected fun event(eventType: ModelEventType, callback: EventClosure<M>) {
        if (!eventMap.containsKey(eventType)) {
            eventMap.put(eventType, mutableListOf())
        }

        eventMap[eventType]!!.add(callback)
    }

    /**
     * Dispatch the registered events for the given type.
     *
     * @param eventType The type to dispatch the events for.
     */
    private fun applyEvent(eventType: ModelEventType) {
        eventMap[eventType]?.forEach { it.apply(model) }
    }

    /**
     * Save the model.
     */
    override fun save() {
        when (created) {

            // Creating the model as row in the database.
            false -> {
                created = true
                applyEvent(ModelEventType.SAVING)
            }

            // Saving the model to the given row in the database.
            true -> {
                applyEvent(ModelEventType.UPDATING)

                applyEvent(ModelEventType.UPDATED)
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
     * {@inheritDoc}
     */
    override fun where(column: String, needle: Any, operand: QueryBuilder.Operand): QueryBuilder<M> {
        wheres.add(Where(wheres.isEmpty(), column, needle, operand))
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun orWhere(column: String, needle: Any, operand: QueryBuilder.Operand): QueryBuilder<M> {
        wheres.add(Where(wheres.isEmpty(), column, needle, operand, Where.Type.OR))
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun andWhere(column: String, needle: Any, operand: QueryBuilder.Operand): QueryBuilder<M> {
        wheres.add(Where(wheres.isEmpty(), column, needle, operand, Where.Type.AND))
        return this
    }

    /**
     * {@inheritDoc}
     */
    override fun <O : AbstractModel<O>> belongsToMany(model: KClass<O>): BelongsToMany<M, O> {
        return BelongsToMany(
            this.model,
            model.createInstance()
        )
    }

    /**
     * {@inheritDoc}
     */
    override fun <O : AbstractModel<O>> belongsTo(model: KClass<O>): BelongsTo<M, O> {
        return BelongsTo(this.model, model.createInstance())
    }

    /**
     * {@inheritDoc}
     */
    override fun <O : AbstractModel<O>> hasMany(model: KClass<O>): HasMany<M, O> {
        return HasMany(this.model, model.createInstance())
    }

    /**
     * {@inheritDoc}
     */
    override fun <O : AbstractModel<O>> hasOne(model: KClass<O>): HasOne<M, O> {
        return HasOne(this.model, model.createInstance())
    }

    override fun build(clazz: KClass<M>): List<M> {
        var result = Command.execute(Command.SELECT, CommandClosure {
            val buildings = StringBuilder()

            wheres.forEach {
                buildings.append(it)
            }

            return@CommandClosure arrayOf(columns.map { it.columnName }.joinToString(", "), table.tableName, buildings.toString())
        })

        return result.resultSet.map {
            var columns = it.value.map {
                it.value
            }

            return@map clazz.primaryConstructor?.call(columns[0])!!
        }
    }

    companion object {
        fun <O : AbstractModel<O>> getSchema(model: KClass<O>): Blueprint<O> {
            val table = MetaReader.readClassAnnotation(model, Table::class)
            val columns = MetaReader.readAllPropertyAnnotations(model, Column::class)
            return Blueprint(model, table, columns)
        }
    }

}