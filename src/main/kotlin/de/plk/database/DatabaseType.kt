package de.plk.database

enum class DatabaseType(
    private val connectionURI: String,
    val configKeys: Array<String>
) {

    SQLITE("sqlite:%s", arrayOf("database")),

    MARIADB("mysql://%s:%s/%s?user=%s&password=%s", arrayOf(
        "hostname",
        "port",
        "database",
        "username",
        "password"
    ));

    fun getConnectionURI(vararg replacements: String): String =
        connectionURI.format(replacements)
}