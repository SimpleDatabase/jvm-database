package de.plk.database.model

import de.plk.database.action.companion.ModelEventType
import de.plk.database.model.event.EventClosure
import de.plk.database.model.meta.Column
import de.plk.database.model.meta.ScopeBy
import de.plk.database.model.meta.Table
import de.plk.database.model.meta.type.ColumnDataType
import de.plk.database.model.relation.one.BelongsTo
import de.plk.database.model.scope.NameScope
import de.plk.database.sql.build.QueryBuilder
import java.util.UUID

/**
 * @author SoftwareBuilds
 * @since 10.02.2024 01:42
 * Copyright © 2024 | SoftwareBuilds | All rights reserved.
 */
@ScopeBy(
    [NameScope::class]
)
@Table("members")
class Member(
    @Column(
        columnName = "memberId",
        primary = true,
        dataType = ColumnDataType.VARCHAR,
        size = 16
    )
    val memberId: UUID
) : AbstractModel<Member>() {

    init {
        boot(this)
    }


    lateinit var name: String


    var age: Int = 0

    override fun boot(model: Member) {
        super.boot(model)

        event(ModelEventType.SAVING, EventClosure<Member> {
            it.name = "test";
            it.save()
        })
    }

    fun underEighteen() {
        where("age", 18, QueryBuilder.Operand.SMALLER)
    }

    fun group(): BelongsTo<Member> {
        return belongsTo(Group::class)
    }
}