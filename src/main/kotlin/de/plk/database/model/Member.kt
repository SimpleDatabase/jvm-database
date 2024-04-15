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
class Member(
    @Column(
        columnName = "memberId",
        primary = true,
        nullable = false,
        dataType = ColumnDataType.INT
    ) var memberId: Int? = null
) : AbstractModel<Member>() {

    constructor(memberId: Int?, name: String): this(memberId) {
        this.name = name
    }

    @Column(
        columnName = "name",
        dataType = ColumnDataType.VARCHAR,
        size = 255
    )
    var name: String? = null

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
        relationType = BelongsTo::class,
        relatedModel = Group::class,
        pivot = true
    )
    fun group(): BelongsTo<Member, Group> {
        return belongsTo(Group::class)
    }

    @Relation(
        relationType = BelongsToMany::class,
        relatedModel = MemberRankPivot::class,
        pivot = true
    )
    fun ranks(): BelongsToMany<Member, MemberRankPivot> {
        return belongsToMany(MemberRankPivot::class)
    }

}