package de.plk.database.meta

enum class ColumnDataType {

    VARCHAR, LONG, TEXT, INT, BOOL;

    fun withSize(size: Int): String {
        return "$name($size)"
    }

}