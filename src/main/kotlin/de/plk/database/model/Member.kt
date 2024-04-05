package de.plk.database.model

import de.plk.database.action.companion.ModelEventType
import de.plk.database.model.meta.*
import de.plk.database.model.meta.type.ColumnDataType
import de.plk.database.model.privot.MemberRankPivot
import de.plk.database.model.relation.many.BelongsToMany
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
class Member() : AbstractModel<Member>() {

    constructor(name: String): this() {
        this.name = name
    }

    @Column(
        columnName = "memberId",
        primary = true,
        dataType = ColumnDataType.VARCHAR,
        size = 16
    )
    val memberId: UUID = UUID.randomUUID()

    @Column(
        columnName = "name",
        dataType = ColumnDataType.VARCHAR,
        size = 255
    )
    var name: String = ""

    init {
        boot(this)
    }

    override fun boot(model: Member) {
        super.boot(model)

        event(ModelEventType.SAVING, {
            println("SAVING memberId: " + it.memberId)
            println("SAVING name: " + it.name)

            it.name = "TEST"

            it.save()
        })

        event(ModelEventType.UPDATED, {
            println("UPDATING memberId: " + it.memberId)
            println("UPDATING name: " + it.name)
        })
    }

    fun underEighteen() {
        where("age", 18, QueryBuilder.Operand.SMALLER)
    }

    @Relation(
        realtionType = BelongsTo::class,
        relatedModel = Group::class
    )
    fun group(): BelongsTo<Member, Group> {
        return belongsTo(Group::class)
    }

    @Relation(
        realtionType = BelongsToMany::class,
        relatedModel = MemberRankPivot::class
    )
    fun ranks(): BelongsToMany<Member, MemberRankPivot> {
        return belongsToMany(MemberRankPivot::class)
    }

}