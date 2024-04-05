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
import java.util.UUID

/**
 * Defines that any subclass is a database model.
 */
@Table(tableName = "member__rank")
class MemberRankPivot() : AbstractModel<MemberRankPivot>() {

    constructor(
        rankId: UUID,
        memberId: UUID
    ) : this() {
        this.rankId = rankId
        this.memberId = memberId
    }

    @Column(
        columnName = "rankId",
        dataType = ColumnDataType.VARCHAR,
        size = 16
    )
    var rankId: UUID? = null

    @Column(
        columnName = "memberId",
        dataType = ColumnDataType.VARCHAR,
        size = 16
    )
    var memberId: UUID? = null

    init {
        boot(this)

        event(ModelEventType.SAVING, {
            println("SAVING memberId: " + it.memberId)
            println("SAVING rankId: " + it.rankId)
        })

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
