package de.plk.database.crud

import de.plk.database.model.AbstractModel

/**
 * Referents the CRUD-principe for any model related to the database.
 *
 * @param <K> The key in the database to find the model.
 * @param <M> The model (table) we aspected to search for.
 */
interface CrudInterface<K, M : AbstractModel> {

    /**
     * Creates a new data-set in the database.
     *
     * @param model The model, that will hold the data.
     */
    fun create(model: M)

    /**
     * Read the models data from the database.
     *
     * @param key The key you want to search the model with.
     *
     * @return The models data from the database.
     */
    fun read(key: K): M

    /**
     * Updates the database with a new data-set.
     *
     * @param key   The positon in the database you want to update.
     * @param model The model with new data.
     */
    fun update(key: K, model: M)

    /**
     * Deletes data in the database on the different key.
     *
     * @param key The key you want to delete in the database.
     */
    fun delete(key: K)

}