package de.plk.database.model.test

import de.plk.database.meta.Column
import de.plk.database.meta.Table
import de.plk.database.meta.type.ColumnDataType
import de.plk.database.model.AbstractModel

/**
 * The test model.
 */
@Table(tableName = "model__users")
data class UserModel(

    @Column(
        primary = true,
        columnName = "user_id",
        dataType = ColumnDataType.INT
    )
    val userId: Int,

    @Column(
        columnName = "user_name",
        dataType = ColumnDataType.VARCHAR
    )
    val userName: String

) : AbstractModel()