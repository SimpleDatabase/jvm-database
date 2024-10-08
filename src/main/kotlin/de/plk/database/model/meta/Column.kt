package de.plk.database.model.meta

import de.plk.database.model.meta.type.ColumnDataType

/**
 * Represents that the field with annotation
 * is a column in the table of the model.
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class Column(

    /**
     * Returns true if this column will be a primary key.
     *
     * @return True if this column will be a primary key.
     */
    val primary: Boolean = false,

    /**
     * Returns true if this column is nullable.
     *
     * @retur True if column is nullable.
     */
    val nullable: Boolean = true,

    /**
     * Returns the data type of the column.
     *
     * @return The data type of the column.
     */
    val dataType: ColumnDataType,

    /**
     * Returns the name of the column.
     *
     * @return The name of the column.
     */
    val columnName: String,

    /**
     * Returns the length of data type of the column.
     *
     * @return The length of data type of the column.
     */
    val size: Int = 255,

)
