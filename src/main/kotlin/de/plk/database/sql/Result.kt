package de.plk.database.sql

interface Result {

    fun getValues(): Map<String, Any>

    fun isValid(): Boolean {
        return getValues().isNotEmpty()
    }

}