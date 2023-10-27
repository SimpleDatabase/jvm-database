package de.plk.database.sql

import de.plk.database.meta.Column

/**
 * Represents a sql-builder with the information from the model.
 */
interface ModelSQLBuilder {

    /**
     * Select command for a SQL-Command.
     *
     * @param columns The columns you want to select.
     *
     * @return The {@link ModelSqlBuilder}.
     */
    fun select(columns: Array<Column>): ModelSQLBuilder

    /**
     * Update a row in the database.
     *
     * @param columns The columns you want to update.
     * @param values  The values you want to update the columns with.
     *
     * @return The {@link ModelSqlBuilder}.
     */
    fun update(columns: Array<Column>, values: Array<Any>): ModelSQLBuilder

    /**
     * Inserts a new row into the database-table.
     *
     * @param columns The columns you want to update.
     * @param values  The values you want to update the columns with.
     *
     * @return The {@link ModelSqlBuilder}.
     */
    fun insert(columns: Array<Column>, values: Array<Any>): ModelSQLBuilder

    /**
     * Deletes an row in the database.
     *
     * @return The {@link ModelSqlBuilder}.
     */
    fun delete(): ModelSQLBuilder

    /**
     * Add a condition to the sql-command.
     *
     * @param column  The column you want to check.
     * @param needle  The value you want to check the column with.
     * @param operand The operand you have to check with the condition (=, <>, !=).
     *
     * @return The {@link ModelSqlBuilder}.
     */
    fun where(column: Column, needle: Any, operand: Operand): ModelSQLBuilder

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
    fun orWhere(column: Column, needle: Any, operand: Operand): ModelSQLBuilder

    /**
     * Add a condition with AND to the sql-command.
     *
     * Add this if you habe already added an {@link #where(String, Object, String)} clausel.
     *
     * @param column  The column you want to check.
     * @param needle  The value you want to check the column with.
     * @param operand The operand you have to check with the condition (=, <>, !=).
     *
     * @return The {@link ModelSqlBuilder}.
     */
    fun andWhere(column: Column, needle: Any, operand: Operand): ModelSQLBuilder

    /**
     * Run the SQL-Comand as an update.
     */
    fun runAsUpdate()

    /**
     * Returns the map with values, if values are present.
     *
     * If the values are not present, the sql-command will be wrong or your request
     * had still a bad request.
     *
     * @return The map with row values if present.
     */
    fun runAsQuery(): Result

    /**
     * The operand for where condition.
     */
    enum class Operand {
        EQUAL, NONEQUAL;
    }
}