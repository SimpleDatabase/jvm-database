package de.plk.database.model.meta

/**
 * Represents that the model is a relational table.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Table(

    /**
     * Returns the name of the table.
     *
     * @return The name of the table.
     */
    val tableName: String

)
