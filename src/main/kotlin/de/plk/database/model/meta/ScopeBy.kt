package de.plk.database.model.meta

import de.plk.database.model.scope.GlobalScope
import kotlin.reflect.KClass

/**
 * Represents that the model is a relational table.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ScopeBy(

    /**
     * Returns the name of the table.
     *
     * @return The name of the table.
     */
    val scopes: Array<KClass<out GlobalScope<*>>>

)
