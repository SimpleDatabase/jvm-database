package de.plk.database

import java.sql.Connection
import java.util.Stack

/**
 * Defines a pool of {@link Connection}`s to support useless watings.
 */
class DatabasePool(

    /**
     * The database information with credentials to create new {@link Connection}`s.
     */
    private val databaseSource: DatabaseSource,

    /**
     * The size of the active connections in the pool.
     */
    private val poolSize: Byte
) {

    /**
     * Creates a new database pool with a default pool size of five.
     *
     * @see DatabasePool#DatabasePool(DatabaseSource, int)
     *
     * @param databaseSource The database information with credentials to create new {@link Connection}`s.
     */
    constructor(databaseSource: DatabaseSource) : this(databaseSource, 5)

    init {
        fill()
    }

    /**
     * Get one of the {@link Connection} from the {@link #POOL}.
     *
     * @return An {@link Connection} of the {@link #POOL}.
     */
    fun getConnection(): Connection {
        if (POOL.isEmpty()) fill()

        val connection: Connection = POOL.pop()

        // Check if the connection is valid or bad.
        if (!isValid(connection)) {
            releaseConnection(connection)
            return getConnection()
        }

        return connection
    }

    /**
     * Check if an {@link Connection} is valid.
     *
     * @param connection The {@link Connection} to check if there is valid.
     *
     * @return The boolean if the {@link Connection} is valid.
     */
    private fun isValid(connection: Connection): Boolean {
        return !connection.isClosed && connection.isValid(1)
    }

    /**
     * Creates a new {@link Connection} with the information from the {@link DatabaseSource}.
     *
     * @return The new {@link Connection}.
     */
    private fun createConnection(): Connection {
        return databaseSource.createConnection()
    }

    /**
     * Releases a connection and put it back to {@link #POOL} if there is valid.
     *
     * If this {@link Connection} is not valid, this function will add a
     * new {@link Connection} to {@link #POOL}.

     * @param connection The {@link Connection} you want to release.
     */
    fun releaseConnection(connection: Connection) {
        if (POOL.size < poolSize) return
        if (isValid(connection)) POOL.push(connection)
        else if (!isValid(connection)) POOL.push(createConnection())
    }

    /**
     * This method will fillup the {@link #POOL} with new {@link Connection}`s.
     */
    private fun fill() {
        for (index in 0..(poolSize - POOL.size))
            POOL.push(createConnection())
    }

    companion object {

        /**
         * The pool of active connections to support useless waitings.
         */
        val POOL: Stack<Connection> = Stack()
    }

}