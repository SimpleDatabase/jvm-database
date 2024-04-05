package de.plk.database.model.meta

import de.plk.database.model.AbstractModel
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotations
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

/**
 * This class holds functions to read meta annotations from a model class.
 */
object MetaReader {

    /**
     * Get the list of A annotations in a model class.
     *
     * @param modelClass The specific model class.
     * @param annotation The specific annotation class.
     *
     * @return <A> The list of A annotations in a model class.
     */
    fun <A : Annotation, M : AbstractModel<M>> readClassAnnotations(
        modelClass: KClass<out M>, annotation: KClass<A>
    ): List<A> {
        return modelClass.findAnnotations(annotation)
    }

    /**
     * Get the list of A annotations in a model class on properties.
     *
     * @param modelClass The specific model class.
     * @param annotation The specific annotation class.
     *
     * @return <A> The list of A annotations in a model class on properties.
     */
    fun <A : Annotation, M : AbstractModel<*>> readAllPropertyAnnotations(
        modelClass: KClass<out M>, annotation: KClass<A>
    ): List<A> {
        return modelClass.memberProperties.filter {
            it.findAnnotations(annotation).size != 0
        }.map {
            it.findAnnotations(annotation).first()
        }
    }

    /**
     * Get the A annotation in a model class.
     *
     * @param modelClass The specific model class.
     * @param annotation The specific annotation class.
     *
     * @return <A> The annotatios in a model class.
     */
    fun <A : Annotation, M : AbstractModel<*>> readClassAnnotation(
        modelClass: KClass<out M>, annotation: KClass<A>
    ): A {
        return modelClass.findAnnotations(annotation).first()
    }

    /**
     * Get the searched property of a model.
     *
     * @param model      The model data.
     * @param annotation The specific annotation class.
     * @param columnName The column to identify the property.
     *
     * @return <M> The property of the model.
     */
    fun <M : AbstractModel<M>> readValue(
        model: M, columnName: String
    ): Any? {
        val property = model::class.memberProperties.filter {
            return@filter it.findAnnotations(Column::class).isNotEmpty() && it.findAnnotations(Column::class)
                .first().columnName == columnName
        }.firstOrNull()

        return property?.call(model)
    }

}