package de.plk.database.model

import de.plk.database.action.companion.ModelEventType
import de.plk.database.model.meta.*
import de.plk.database.model.meta.type.ColumnDataType
import de.plk.database.model.relation.one.HasOne

/**
 * @author SoftwareBuilds
 * @since 10.02.2024 01:42
 * Copyright © 2024 | SoftwareBuilds | All rights reserved.
 */
@Table("groups")
class Group(
    id: Int? = null
) : AbstractModel<Group>(id) {

    constructor(id: Int? = null, hasOne: Boolean) : this(id) {}

    @Column(
        columnName = "groupName",
        nullable = true,
        dataType = ColumnDataType.VARCHAR,
        size = 255
    )
    var groupName: String? = null

    @Column(
        columnName = "kuerzel",
        nullable = true,
        dataType = ColumnDataType.VARCHAR,
        size = 32
    )
    var kuerzel: String? = null

    init {
        boot(this)
    }

    override fun boot(model: Group) {
        super.boot(model)

        event(ModelEventType.SAVING) {
            println("Element with ID (${it.id}) saving.")
            println("Value of groupId ${MetaReader.readValue(this, "groupId")}")
        }

        event(ModelEventType.UPDATING) {
            println("Element with ID (${it.id}) updated.")
        }

        event(ModelEventType.DELETED) {
            println("Element with ID ($id) deleted.")
        }

    }

    @Relation(
        relationType = HasOne::class,
        relatedModel = Member::class,
    )
    fun member(): HasOne<Group, Member> {
        return hasOne(Member::class)
    }

}
