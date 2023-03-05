package de.plk.database

import java.sql.Connection
import java.sql.DriverManager
import java.util.Properties

class DatabaseSource(
    val databaseType: DatabaseType,
    val properties: Properties
) {

    private val cache: List<String> = listOf()

    init {
        if (properties.isEmpty)
            throw RuntimeException("The properties file cannot be empty.")

        databaseType.configKeys.forEach {

            val property: String = properties.getProperty(it)
                ?: throw RuntimeException("The property $it is not set.")

            cache.plus(property)
        }
    }

    fun createConnection(): Connection = DriverManager.getConnection(
        databaseType.getConnectionURI(
            *cache.toTypedArray()
        )
    )

}