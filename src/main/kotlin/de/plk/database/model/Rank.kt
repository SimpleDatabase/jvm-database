package de.plk.database.model

import de.plk.database.model.meta.Column
import de.plk.database.model.meta.Relation
import de.plk.database.model.meta.Table
import de.plk.database.model.meta.type.ColumnDataType
import de.plk.database.model.privot.MemberRankPivot
import de.plk.database.model.relation.many.BelongsToMany

/**
 * @author SoftwareBuilds
 * @since 10.02.2024 01:42
 * Copyright © 2024 | SoftwareBuilds | All rights reserved.
 */
@Table("ranks")
class Rank(
    @Column(
        columnName = "rankId",
        primary = true,
        nullable = false,
        dataType = ColumnDataType.INT,
    ) val rankId: Int
) : AbstractModel<Rank>() {

    constructor(rankId: Int, name: String): this(rankId) {
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

    @Relation(
        relationType = BelongsToMany::class,
        relatedModel = MemberRankPivot::class,
        pivot = true
    )
    fun members(): BelongsToMany<Rank, MemberRankPivot> {
        return belongsToMany(MemberRankPivot::class)
    }

}