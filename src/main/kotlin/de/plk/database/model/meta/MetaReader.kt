package de.plk.database.model.meta

import de.plk.database.model.AbstractModel
import java.lang.reflect.Field
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties

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
    fun <A : Annotation> readFieldAnnotations(
        modelClass: KClass<out AbstractModel>, annotation: KClass<A>
    ): List<A> {
        return modelClass.annotations.filterIsInstance(annotation.java)
    }

    /**
     * Get the A annotation in a model class.
     *
     * @param modelClass The specific model class.
     * @param annotation The specific annotation class.
     *
     * @return <A> The annotatios in a model class.
     */
    fun <A : Annotation> readClassAnnotation(
        modelClass: KClass<out AbstractModel>, annotation: KClass<A>
    ): A {
        return modelClass.annotations.filterIsInstance(annotation.java).first()
    }

    fun <M : AbstractModel, A : Annotation> readValue(
        model: M, annotation: KClass<A>, columnName: String
    ): KProperty1<out M, *> {
        return model::class.memberProperties.filter {
           it.annotations.filterIsInstance(annotation.java).size == 1
        }.first()
    }
}