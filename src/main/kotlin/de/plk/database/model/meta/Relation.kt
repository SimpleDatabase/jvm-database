package de.plk.database.model.meta

import de.plk.database.model.AbstractModel
import de.plk.database.model.relation.Relation
import de.plk.database.model.relation.one.BelongsTo
import kotlin.reflect.KClass

/**
 * Represents that the field with annotation
 * is a column in the table of the model.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Relation(

    val realtionType: KClass<out Relation<*, *>> = BelongsTo::class,

    val relatedModel: KClass<out AbstractModel<*>>

)
