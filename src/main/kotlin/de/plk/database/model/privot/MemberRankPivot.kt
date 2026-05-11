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

/**
 * Defines that any subclass is a database model.
 */
@Table(tableName = "member__rank")
class MemberRankPivot(id: Int
) : AbstractModel<MemberRankPivot>(id) {

    constructor(): this(0)

    constructor(
        id: Int,
        ranks_id: Int,
        members_id: Int
    ) : this(id) {
        this.ranks_id = ranks_id
        this.members_id = members_id
    }

    @Column(
        columnName = "ranks_id",
        dataType = ColumnDataType.INT,
    )
    var ranks_id: Int? = null

    @Column(
        columnName = "members_id",
        dataType = ColumnDataType.INT,
    )
    var members_id: Int? = null

    init {
        boot(this)

        event(ModelEventType.SAVING) {
            println("SAVING memberId: " + it.members_id)
            println("SAVING rankId: " + it.ranks_id)
        }

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
