package de.plk.database.sql.relation

import de.plk.database.model.AbstractModel
import de.plk.database.model.relation.many.HasMany
import de.plk.database.model.relation.one.HasOne
import kotlin.reflect.KClass

/**
 * Represents the builder to add has relations to a model.
 */
interface HasBuilder<M : AbstractModel<M>> {

    /**
     * Creates a has many relation.
     *
     * @param model The related model.
     *
     * @return The has many relation.
     */
    fun <O : AbstractModel<O>> hasMany(model: KClass<O>): HasMany<M>

    /**
     * Creates a has one relation.
     *
     * @param model The related model.
     *
     * @return The has one relation.
     */
    fun <O : AbstractModel<O>> hasOne(model: KClass<O>): HasOne<M>

}