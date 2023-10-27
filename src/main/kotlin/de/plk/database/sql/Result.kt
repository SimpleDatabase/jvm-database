package de.plk.database.sql

/**
 * Represents the result of an Query.
 *
 * @see ResultSet
 */
interface Result {

    /**
     * The map of results if present.
     *
     * @return The results as an <K, V> map.
     * @throws SQLException
     */
    fun getValues(): Map<String, Any>

    /**
     * Checks if a result is invalid because of no results or interupption.
     *
     * @return The information is this request is still invalid or not.
     * @throws SQLException
     */
    fun isValid(): Boolean {
        return getValues().isNotEmpty()
    }

}