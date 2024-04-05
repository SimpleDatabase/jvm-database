package de.plk.database.model.privot

import de.plk.database.model.AbstractModel
import de.plk.database.model.Member
import de.plk.database.model.Rank
import de.plk.database.model.meta.Column
import de.plk.database.model.meta.Relation
import de.plk.database.model.meta.Table
import de.plk.database.model.meta.type.ColumnDataType
import de.plk.database.model.relation.many.BelongsToMany
import java.util.UUID

/**
 * Defines that any subclass is a database model.
 */
@Table(tableName = "member__rank")
class MemberRankPivot : AbstractModel<MemberRankPivot>() {

    @Column(
        columnName = "rankId",
        dataType = ColumnDataType.VARCHAR,
        size = 16
    )
    lateinit var rankId: UUID

    @Column(
        columnName = "memberId",
        dataType = ColumnDataType.VARCHAR,
        size = 16
    )
    lateinit var memberId: UUID

    init {
        boot(this)
    }

    @Relation(
        relatedModel = Member::class,
        realtionType = BelongsToMany::class
    )
    fun members(): BelongsToMany<MemberRankPivot, Member> {
        return belongsToMany(Member::class)
    }

    @Relation(
        relatedModel = Rank::class,
        realtionType = BelongsToMany::class
    )
    fun ranks(): BelongsToMany<MemberRankPivot, Rank> {
        return belongsToMany(Rank::class)
    }
}