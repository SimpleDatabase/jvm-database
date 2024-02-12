package de.plk.database.model

import de.plk.database.action.companion.ModelEventType
import de.plk.database.model.event.EventClosure
import de.plk.database.model.meta.Column
import de.plk.database.model.meta.type.ColumnDataType
import de.plk.database.model.relation.many.HasMany
import java.util.UUID

/**
 * @author SoftwareBuilds
 * @since 10.02.2024 01:42
 * Copyright © 2024 | SoftwareBuilds | All rights reserved.
 */
class Group(
    @Column(
        columnName = "groupId",
        primary = true,
        dataType = ColumnDataType.VARCHAR,
        size = 16
    )
    val groupId: UUID = TODO()
) : AbstractModel() {

    @Column(
        columnName = "name",
        dataType = ColumnDataType.VARCHAR
    )
    lateinit var name: String
    override fun boot() {
        event(ModelEventType.SAVING, EventClosure<Group> {
            it.name = "test";
            it.save()
        })
    }

    fun members(): HasMany? {
        return hasMany(Member::class)
    }
}