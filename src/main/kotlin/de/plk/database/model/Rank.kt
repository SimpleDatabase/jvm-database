package de.plk.database.model

import de.plk.database.model.meta.Column
import de.plk.database.model.meta.Relation
import de.plk.database.model.meta.Table
import de.plk.database.model.meta.type.ColumnDataType
import de.plk.database.model.privot.MemberRankPivot
import de.plk.database.model.relation.many.BelongsToMany
import java.util.UUID

/**
 * @author SoftwareBuilds
 * @since 10.02.2024 01:42
 * Copyright © 2024 | SoftwareBuilds | All rights reserved.
 */
@Table("ranks")
class Rank() : AbstractModel<Rank>() {

    constructor(name: String): this() {
        this.name = name
    }

    @Column(
        columnName = "rankId",
        primary = true,
        dataType = ColumnDataType.VARCHAR,
        size = 16
    )
    val rankId: UUID = UUID.randomUUID()

    @Column(
        columnName = "name",
        dataType = ColumnDataType.VARCHAR,
        size = 255
    )
    var name: String = ""

    init {
        boot(this)
    }

    @Relation(
        realtionType = BelongsToMany::class,
        relatedModel = MemberRankPivot::class
    )
    fun members(): BelongsToMany<Rank, MemberRankPivot> {
        return belongsToMany(MemberRankPivot::class)
    }

}