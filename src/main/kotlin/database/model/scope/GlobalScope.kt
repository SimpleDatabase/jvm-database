package de.plk.database.model.scope

import de.plk.database.model.AbstractModel

/**
 * Represents the abstract global scope for a model.
 */
interface GlobalScope<M : AbstractModel<M>> {

    /**
     * The function for the scope restriction.
     *
     * @param model The model with model data and builder.
     */
    fun scope(model: M)

}