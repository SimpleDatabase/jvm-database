package de.plk.database.meta

import de.plk.database.model.AbstractModel
import kotlin.reflect.KClass

object MetaReader {

    fun <A : Annotation> readFieldAnnotations(
        modelClass: KClass<out AbstractModel>, annotation: KClass<A>
    ): List<A> {
        return modelClass.annotations.filterIsInstance(annotation.java)
    }

    fun <A : Annotation> readClassAnnotation(
        modelClass: KClass<out AbstractModel>, annotation: KClass<A>
    ): A {
        return modelClass.annotations.filterIsInstance(annotation.java).first()
    }
}