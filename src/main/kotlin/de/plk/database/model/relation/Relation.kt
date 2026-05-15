package de.plk.database.model.relation

import de.plk.database.model.AbstractModel
import de.plk.database.sql.build.QueryBuilder
import kotlin.reflect.KClass

/**
 * Represents the abstract relation.
 */
open class Relation<M : AbstractModel<M>, O : AbstractModel<O>>(

    /**
     * The query builder of the base model.
     */
    val builder: QueryBuilder<M>,

    /**
     * The related model.
     */
    open val related: Int,

    open val relatedModelClass: KClass<O>
) {
    /**
     * The related model.
     */
    open fun getRelated(): O? {
        return AbstractModel.loadFromId(
            relatedModelClass, related
        )
    }
}
