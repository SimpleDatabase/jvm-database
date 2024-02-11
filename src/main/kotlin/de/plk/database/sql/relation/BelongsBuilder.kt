package de.plk.database.sql.relation

import de.plk.database.model.AbstractModel
import de.plk.database.model.relation.many.BelongsToMany
import de.plk.database.model.relation.one.BelongsTo
import kotlin.reflect.KClass

/**
 * @author SoftwareBuilds
 * @since 11.02.2024 22:12
 * Copyright © 2024 | SoftwareBuilds | All rights reserved.
 */
interface BelongsBuilder {

    fun <M : AbstractModel> belongsToMany(model: KClass<M>): BelongsToMany

    fun <M : AbstractModel> belongsTo(model: KClass<M>): BelongsTo

}