package de.plk.database

/**
 * Represents the type (engine) of the sql-server.
 *
 * e.g.: MariaDB, MYSQL, SQLite
 */
enum class DatabaseType(

    /**
     * The connection uri for the {@link java.sql.DriverManager#getConnection(String)} method.
     */
    private val connectionURI: String,

    /**
     * The requirements for the Authentication to the sql-daemon.
     */
    val configKeys: Array<String>
) {

    /**
     * SQLite.
     */
    SQLITE("jdbc:sqlite:%s", arrayOf("database")),

    /**
     * MariaDB/ MySQL.
     */
    MARIADB("jdbc:mysql://%s:%s/%s?user=%s&password=%s", arrayOf(
        "hostname",
        "port",
        "database",
        "username",
        "password"
    ));

    /**
     * Get the connection URI with the specific data.
     *
     * @param replacements The specific data as a replacement.
     *
     * @return The connection URI value as string.
     */
    fun getConnectionURI(vararg replacements: String): String {
        return connectionURI.format(*replacements)
    }
}