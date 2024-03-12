package de.plk.database.model.privot

import de.plk.database.model.AbstractModel
import de.plk.database.model.Member
import de.plk.database.model.Rank
import de.plk.database.model.meta.Column
import de.plk.database.model.meta.Table
import de.plk.database.model.meta.type.ColumnDataType
import de.plk.database.model.relation.many.HasMany
import de.plk.database.model.relation.one.BelongsTo
import java.util.UUID
import kotlin.reflect.KClass

/**
 * Defines that any subclass is a database model.
 */
@Table(tableName = "member__rank")
class MemberRankPivot : PivotModel<MemberRankPivot, Member, Rank>() {

    @Column(
        columnName = "rankId",
        primary = true,
        dataType = ColumnDataType.VARCHAR,
        size = 16
    )
    lateinit var rankId: UUID

    @Column(
        columnName = "memberId",
        primary = true,
        dataType = ColumnDataType.VARCHAR,
        size = 16
    )
    lateinit var memberId: UUID

    init {
        boot(this)

        members()
    }

    fun members(): HasMany<MemberRankPivot, Member> {
        return hasMany(Member::class)
    }
}