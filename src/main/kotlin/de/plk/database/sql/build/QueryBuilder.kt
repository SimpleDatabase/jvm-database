package de.plk.database.sql.build

import de.plk.database.model.AbstractModel
import de.plk.database.sql.relation.BelongsBuilder
import de.plk.database.sql.relation.HasBuilder

/**
 * Represents a sql-builder with the information from the model.
 */
interface QueryBuilder<M : AbstractModel<M>> : BelongsBuilder<M>, HasBuilder<M> {

    /**
     * Add a condition to the sql-command.
     *
     * @param column  The column you want to check.
     * @param needle  The value you want to check the column with.
     * @param operand The operand you have to check with the condition (=, <>, !=).
     *
     * @return The {@link ModelSqlBuilder}.
     */
    fun where(column: String, needle: Any, operand: Operand = Operand.EQUAL): QueryBuilder<M>

    /**
     * Add a condition with OR to the sql-command.
     *
     * Add this if you habe already added an {@link #where(String, Object, String)} clausel.
     *
     * @param column  The column you want to check.
     * @param needle  The value you want to check the column with.
     * @param operand The operand you have to check with the condition (=, <>, !=).
     *
     * @return The {@link ModelSqlBuilder}.
     */
    fun orWhere(column: String, needle: Any, operand: Operand = Operand.EQUAL): QueryBuilder<M>

    /**
     * Add a condition with AND to the sql-command.
     *
     * Add this if you habe already added an {@link #where(String, Object, String)} clausel.
     *
     * @param column  The column you want to check.
     * @param needle  The value you want to check the column with.
     * @param operand The operand you have to check with the condition (=, <>, !=).
     *
     * @return The {@link ModelSqlBuilder}.w
     */
    fun andWhere(column: String, needle: Any, operand: Operand = Operand.EQUAL): QueryBuilder<M>

    /**
     * The operand for where condition.
     */
    enum class Operand(
        val operand: String
    ) {
        EQUAL("="),
        NONEQUAL("!="),
        LIKE("LIKE"),
        GREATHER(">"),
        GREATHER_THAN(">="),
        SMALLER("<"),
        SMALLER_THAN("<=");
    }

}