package de.plk.database.meta

/**
 * Definies the column data-type of an sql-server engine.
 */
enum class ColumnDataType {

    VARCHAR, LONG, TEXT, INT, BOOL;

    /**
     * Returns The length of the data-type.
     *
     * @param size The length of the data-type.
     *
     * @return The string for the sql-command.
     */
    fun withSize(size: Int): String {
        return "$name($size)"
    }

}