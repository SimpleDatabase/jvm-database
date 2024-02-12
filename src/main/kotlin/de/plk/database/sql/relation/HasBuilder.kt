package de.plk.database.sql.relation

import de.plk.database.model.AbstractModel
import de.plk.database.model.relation.many.HasMany
import de.plk.database.model.relation.one.HasOne
import kotlin.reflect.KClass

/**
 * Represents the builder to add has relations to a model.
 */
interface HasBuilder {

    /**
     * Creates a has many relation.
     *
     * @param model The related model.
     *
     * @return The has many relation.
     */
    fun <M : AbstractModel> hasMany(model: KClass<M>): HasMany

    /**
     * Creates a has one relation.
     *
     * @param model The related model.
     *
     * @return The has one relation.
     */
    fun <M : AbstractModel> hasOne(model: KClass<M>): HasOne

}