package de.plk.database.sql.command.condition

import de.plk.database.sql.build.QueryBuilder

/**
 * Represents the where condition on a sql command.
 */
data class Where(

    /**
     * The string of the column to check.
     */
    val column: String,

    /**
     * The needle element to restrict for.
     */
    val needle: Any,

    /**
     * The used operand (EQUAL, NONEQUAL, ...).
     */
    val operand: QueryBuilder.Operand = QueryBuilder.Operand.EQUAL,

    /**
     * The where type (AND, OR, NORMAL).
     */
    val type: Type = Type.NORMAL
) {

    /**
     * {@inheritDoc}
     */
    override fun toString(): String {
        val where = StringBuilder()

        when (type) {
            Type.OR -> where.append(" OR ")
            Type.AND, Type.NORMAL -> where.append(" AND ")
        }

        where.append("WHERE ${column} ${operand.operand} ${needle}")

        return where.toString()
    }

    /**
     * Represents type of where.
     */
    enum class Type {
        OR, AND, NORMAL
    }
}
