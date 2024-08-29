package de.plk.database.model.privot

import de.plk.database.action.companion.ModelEventType
import de.plk.database.model.AbstractModel
import de.plk.database.model.Member
import de.plk.database.model.Rank
import de.plk.database.model.meta.Column
import de.plk.database.model.meta.Relation
import de.plk.database.model.meta.Table
import de.plk.database.model.meta.type.ColumnDataType
import de.plk.database.model.relation.many.BelongsToMany
import de.plk.database.model.relation.many.HasMany
import java.util.UUID

/**
 * Defines that any subclass is a database model.
 */
@Table(tableName = "member__rank")
class MemberRankPivot(
    @Column(
        columnName = "pivotId",
        primary = true,
        nullable = false,
        dataType = ColumnDataType.INT,
    ) val pivotId: Int
) : AbstractModel<MemberRankPivot>() {

    constructor(): this(0)

    constructor(
        pivotId: Int,
        rankId: Int,
        memberId: Int
    ) : this(pivotId) {
        this.rankId = rankId
        this.memberId = memberId
    }

    @Column(
        columnName = "rankId",
        dataType = ColumnDataType.INT,
    )
    var rankId: Int? = null

    @Column(
        columnName = "memberId",
        dataType = ColumnDataType.INT,
    )
    var memberId: Int? = null

    init {
        boot(this)

        event(ModelEventType.SAVING, {
            println("SAVING memberId: " + it.memberId)
            println("SAVING rankId: " + it.rankId)
        })

    }

    @Relation(
        relatedModel = Member::class,
        relationType = BelongsToMany::class
    )
    fun members(): HasMany<MemberRankPivot, Member> {
        return hasMany(Member::class)
    }

    @Relation(
        relatedModel = Rank::class,
        relationType = BelongsToMany::class,
    )
    fun ranks(): HasMany<MemberRankPivot, Rank> {
        return hasMany(Rank::class)
    }

}
