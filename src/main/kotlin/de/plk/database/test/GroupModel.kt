package de.plk.database.test

import de.plk.database.model.meta.Column
import de.plk.database.model.meta.Relation
import de.plk.database.model.meta.Table
import de.plk.database.model.meta.type.ColumnDataType
import de.plk.database.model.meta.type.relation.RelationType
import de.plk.database.model.AbstractModel

/**
 * The test model.
 */
@Table(tableName = "model__groups")
data class GroupModel(

    @Column(
        primary = true,
        columnName = "group_id",
        dataType = ColumnDataType.INT
    )
    val groupId: Int,

    @Column(
        columnName = "group_name",
        dataType = ColumnDataType.VARCHAR
    )
    val groupName: String,

    @Relation(
        relationType = RelationType.ONE_TO_MANY,
        foreignModel = UserModel::class
    )
    val members: List<UserModel>

) : AbstractModel()
