package de.plk.database.sql.relation

import de.plk.database.model.AbstractModel
import de.plk.database.model.relation.many.BelongsToMany
import de.plk.database.model.relation.one.BelongsTo
import kotlin.reflect.KClass

/**
 * Represents the builder to add belongs relations to a model.
 */
interface BelongsBuilder<M : AbstractModel<M>> {

    /**
     * Creates a belongs many relation.
     *
     * @param model The related model.
     *
     * @return The belongs many relation.
     */
    fun <O : AbstractModel<O>> belongsToMany(model: KClass<O>): BelongsToMany<M, O>

    /**
     * Creates a belongs one relation.
     *
     * @param model The related model.
     *
     * @return The belongs one relation.
     */
    fun <O : AbstractModel<O>> belongsTo(model: KClass<O>): BelongsTo<M, O>

}