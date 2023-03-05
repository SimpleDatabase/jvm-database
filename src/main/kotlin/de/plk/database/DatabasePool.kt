package de.plk.database

import java.sql.Connection
import java.util.Stack

class DatabasePool(
    private val databaseSource: DatabaseSource,
    private val poolSize: Byte
) {

    constructor(databaseSource: DatabaseSource) : this(databaseSource, 5)

    init {
        fill()
    }

    fun getConnection(): Connection {
        if (POOL.isEmpty()) fill()

        val connection: Connection = POOL.pop()
        if (!isValid(connection)) {
            releaseConnection(connection)
            return getConnection()
        }

        return connection
    }

    private fun isValid(connection: Connection): Boolean {
        return !connection.isClosed && connection.isValid(1)
    }

    private fun createConnection(): Connection {
        return databaseSource.createConnection()
    }

    fun releaseConnection(connection: Connection) {
        if (POOL.size < poolSize) return
        if (isValid(connection)) POOL.push(connection)
        else if (!isValid(connection)) POOL.push(createConnection())
    }

    private fun fill() {
        for (index in 0..(poolSize - POOL.size))
            POOL.push(createConnection())
    }

    companion object {
        val POOL: Stack<Connection> = Stack()
    }

}