package de.plk.database.sql.command.condition

import de.plk.database.sql.build.QueryBuilder

/**
 * Represents the where condition on a sql command.
 */
data class Where(

    /**
     * True if this is the first where in a sql command.
     */
    val first: Boolean = false,

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

        if (!first) {
            when (type) {
                Type.OR -> where.append("OR ")
                Type.AND, Type.NORMAL -> where.append("AND ")
            }
        } else {
            where.append("WHERE ")
        }

        when (operand) {
            QueryBuilder.Operand.LIKE -> where.append("$column ${operand.operand} '$needle'")
            else -> where.append("$column ${operand.operand} $needle")
        }

        return where.toString()
    }

    /**
     * Represents type of where.
     */
    enum class Type {
        OR, AND, NORMAL
    }
}
