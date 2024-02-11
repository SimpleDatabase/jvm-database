package de.plk.database.model.scope

import de.plk.database.model.AbstractModel

/**
 * @author SoftwareBuilds
 * @since 10.02.2024 11:45
 * Copyright © 2024 | SoftwareBuilds | All rights reserved.
 */
interface Scope<M : AbstractModel> {

    fun scope(model: M)

}