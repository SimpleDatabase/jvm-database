package de.plk.database

import java.sql.Connection
import java.sql.DriverManager
import java.util.Properties

/**
 * Holds basic information for new database {@link Connection}`s.
 * <p>
 * Also this class will hold basic information for the {@link DatabasePool}.
 */
class DatabaseSource(

    /**
     * The sql-server engine type for the connection building.
     */
    private val databaseType: DatabaseType,

    /**
     * The replacements (inc. the credentials) for the database connection-URI.
     */
    private val properties: Properties
) {

    /**
     * The replacements (inc. the credentials) for the database connection-URI.
     */
    private val cache: List<String> = listOf()

    init {
        if (properties.isEmpty)
            throw RuntimeException("The properties file cannot be empty.")

        // Push information from the requirements variable to the replacement variable (credentials).
        databaseType.configKeys.forEach {
            val property: String = properties.getProperty(it)
                ?: throw RuntimeException("The property $it is not set.")

            cache.plus(property)
        }
    }

    /**
     * Creates a new database {@link Connection}.
     *
     * @return The database {@link Connection}.
     * @throws Exception
     */
    fun createConnection(): Connection = DriverManager.getConnection(
        databaseType.getConnectionURI(
            *cache.toTypedArray()
        )
    )

}