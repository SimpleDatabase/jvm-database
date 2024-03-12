package de.plk.database.model

import de.plk.database.action.companion.ModelEventType
import de.plk.database.model.event.EventClosure
import de.plk.database.model.meta.Column
import de.plk.database.model.meta.ScopeBy
import de.plk.database.model.meta.Table
import de.plk.database.model.meta.type.ColumnDataType
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
@Table("ranks")
class Rank : AbstractModel<Rank>() {

    @Column(
        columnName = "rankId",
        primary = true,
        dataType = ColumnDataType.VARCHAR,
        size = 16
    )
    val rankId: UUID = UUID.randomUUID()

    init {
        boot(this)
    }

    fun members(): BelongsToMany<Rank, Member> {
        return belongsToMany(Member::class)
    }
}