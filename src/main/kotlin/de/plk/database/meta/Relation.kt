package de.plk.database.meta

import de.plk.database.meta.type.relation.RelationType
import de.plk.database.model.AbstractModel
import kotlin.reflect.KClass

/**
 * Represents that the model is related to another model.
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Relation(

    /**
     * Returns the relation type.
     *
     * @return The relation type.
     */
    val relationType: RelationType,

    /**
     * Returns the foreign model class.
     *
     * @return The foreign model class.
     */
    val foreignModel: KClass<out AbstractModel>

)
