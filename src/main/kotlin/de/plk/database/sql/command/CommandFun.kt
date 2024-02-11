package de.plk.database.sql.command

import de.plk.database.model.AbstractModel

/**
 * @author SoftwareBuilds
 * @since 11.02.2024 22:45
 * Copyright © 2024 | SoftwareBuilds | All rights reserved.
 */
fun interface CommandClosure {

    fun apply(): Array<String>

}