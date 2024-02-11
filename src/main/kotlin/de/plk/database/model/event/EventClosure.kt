package de.plk.database.model.event

import de.plk.database.model.AbstractModel

/**
 * @author SoftwareBuilds
 * @since 10.02.2024 00:55
 * Copyright © 2024 | SoftwareBuilds | All rights reserved.
 */
fun interface EventClosure<M : AbstractModel> {

    fun apply(model: M)

}