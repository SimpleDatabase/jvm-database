package de.plk.database.sql.build

/**
 * @author SoftwareBuilds
 * @since 16.02.2024 20:35
 * Copyright © 2024 | SoftwareBuilds | All rights reserved.
 */
interface DumpableInterface {

    /**
     * DEBUGGING.
     * Get the sql command with bindings.
     *
     * @return The sql command with bindings.
     */
    fun dump(): String

}