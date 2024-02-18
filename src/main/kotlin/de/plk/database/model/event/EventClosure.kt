package de.plk.database.model.event

import de.plk.database.model.AbstractModel

/**
 * Represents the closure for a model event.
 *
 * With this u can add statements to the operation like (creating, updating) of
 * the model.
 */
fun interface EventClosure<M : AbstractModel<M>> {

    /**
     * Appliement function for the event closure.
     *
     * @param model The model to dispatch the event on.
     */
    fun apply(model: M)

}