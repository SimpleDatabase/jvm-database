package de.plk.database.action.operation

/**
 * @author SoftwareBuilds
 * @since 10.02.2024 01:13
 * Copyright © 2024 | SoftwareBuilds | All rights reserved.
 */
interface ModelOperation {

    /**
     * Saves the model to the database.
     */
    fun save()

    /**
     * Deletes the model from the database.
     */
    fun delete()

    /**
     * Loads the model from the database.
     */
    fun load()

}