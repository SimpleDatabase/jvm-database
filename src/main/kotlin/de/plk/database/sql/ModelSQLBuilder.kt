package de.plk.database.sql

import de.plk.database.meta.Column

interface ModelSQLBuilder {

    fun select(columns: Array<Column>): ModelSQLBuilder

    fun update(columns: Array<Column>, values: Array<Any>): ModelSQLBuilder

    fun insert(columns: Array<Column>, values: Array<Any>): ModelSQLBuilder

    fun delete(): ModelSQLBuilder

    fun where(column: Column, needle: Any, operand: Operand): ModelSQLBuilder

    fun orWhere(column: Column, needle: Any, operand: Operand): ModelSQLBuilder

    fun andWhere(column: Column, needle: Any, operand: Operand): ModelSQLBuilder

    fun runAsUpdate()

    fun runAsQuery(): Result

    enum class Operand {
        EQUAL, NONEQUAL;
    }
}