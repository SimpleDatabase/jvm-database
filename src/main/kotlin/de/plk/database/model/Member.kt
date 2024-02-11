package de.plk.database.model

import de.plk.database.action.companion.ModelEventType
import de.plk.database.model.event.EventClosure
import de.plk.database.model.meta.Column
import de.plk.database.model.meta.type.ColumnDataType
import de.plk.database.model.relation.many.BelongsToMany
import de.plk.database.model.scope.NameScope
import java.util.UUID

/**
 * @author SoftwareBuilds
 * @since 10.02.2024 01:42
 * Copyright © 2024 | SoftwareBuilds | All rights reserved.
 */
class Member(
    @Column(
        columnName = "memberId",
        primary = true,
        dataType = ColumnDataType.VARCHAR,
        size = 16
    )
    val memberId: UUID = TODO()
) : AbstractModel() {

    @Column(
        columnName = "name",
        dataType = ColumnDataType.VARCHAR
    )
    lateinit var name: String
    override fun boot() {
        event(ModelEventType.SAVING, EventClosure<Member> {
            it.name = "test";
            it.save()
        })

        addGlobalScope(NameScope())
    }

    fun groups(): BelongsToMany? {
        return null
    }
}