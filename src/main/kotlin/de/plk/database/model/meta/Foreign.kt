package de.plk.database.model.meta

import de.plk.database.model.AbstractModel
import kotlin.reflect.KClass

/**
 * Represents that the field with annotation
 * is column in the table of the model.
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Foreign(

    val modelClass: KClass<out AbstractModel> = AbstractModel::class,

    val foreignKey: String = ""

)
