package de.plk.database.sql.relation

import de.plk.database.model.AbstractModel
import de.plk.database.model.relation.many.HasMany
import de.plk.database.model.relation.one.HasOne
import kotlin.reflect.KClass

/**
 * @author SoftwareBuilds
 * @since 11.02.2024 22:13
 * Copyright © 2024 | SoftwareBuilds | All rights reserved.
 */
interface HasBuilder {

    /**
     *
     */
    fun <M : AbstractModel> hasMany(model: KClass<M>): HasMany

    /**
     *
     */
    fun <M : AbstractModel> hasOne(model: KClass<M>): HasOne

}