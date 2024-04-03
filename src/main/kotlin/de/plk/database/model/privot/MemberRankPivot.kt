package de.plk.database.model.privot

import de.plk.database.model.AbstractModel
import de.plk.database.model.Group
import de.plk.database.model.Member
import de.plk.database.model.Rank
import de.plk.database.model.meta.Column
import de.plk.database.model.meta.Relation
import de.plk.database.model.meta.Table
import de.plk.database.model.meta.type.ColumnDataType
import de.plk.database.model.relation.many.BelongsToMany
import de.plk.database.model.relation.many.HasMany
import de.plk.database.model.relation.many.ToPivot
import de.plk.database.model.relation.one.BelongsTo
import java.util.UUID
import kotlin.reflect.KClass

/**
 * Defines that any subclass is a database model.
 */
@Table(tableName = "member__rank")
class MemberRankPivot<M : AbstractModel<M>, N : AbstractModel<N>> : PivotModel<MemberRankPivot<M, N>, M, N>() {

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

    fun members(): HasMany<MemberRankPivot<M, N>, Member> {
        return hasMany(Member::class)
    }
}