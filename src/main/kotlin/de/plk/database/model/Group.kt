package de.plk.database.model

import de.plk.database.action.companion.ModelEventType
import de.plk.database.model.event.EventClosure
import de.plk.database.model.meta.Column
import de.plk.database.model.meta.MetaReader
import de.plk.database.model.meta.ScopeBy
import de.plk.database.model.meta.Table
import de.plk.database.model.meta.type.ColumnDataType
import de.plk.database.model.relation.many.HasMany
import de.plk.database.model.scope.NameScope
import java.util.UUID

/**
 * @author SoftwareBuilds
 * @since 10.02.2024 01:42
 * Copyright © 2024 | SoftwareBuilds | All rights reserved.
 */
@ScopeBy(
    [NameScope::class]
)
@Table("groups")
class Group : AbstractModel<Group>() {

    @Column(
        columnName = "groupId",
        primary = true,
        nullable = false,
        dataType = ColumnDataType.VARCHAR,
        size = 16
    )
    val groupId: UUID = UUID.randomUUID()

    @Column(
        columnName = "groupName",
        nullable = true,
        dataType = ColumnDataType.VARCHAR,
        size = 255
    )
    lateinit var groupName: String

    @Column(
        columnName = "kuerzel",
        nullable = true,
        dataType = ColumnDataType.VARCHAR,
        size = 32
    )
    lateinit var kuerzel: String

    init {
        boot(this)
    }

    override fun boot(model: Group) {
        super.boot(model)

        event(ModelEventType.SAVING, EventClosure<Group> {
            println("Element with ID (${it.groupId}) saving.")

            println("Value of groupId ${MetaReader.readValue(this, "groupId")}")
        })

        event(ModelEventType.UPDATING, EventClosure<Group> {
            println("Element with ID (${it.groupId}) updated.")
        })

        event(ModelEventType.DELETED, EventClosure<Group> {
            println("Element with ID ($groupId) deleted.")
        })

    }

}