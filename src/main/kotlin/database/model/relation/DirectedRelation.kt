package de.plk.database.model.relation

import de.plk.database.model.AbstractModel
import de.plk.database.sql.build.QueryBuilder
import kotlin.reflect.KClass

/**
 * Represents the abstract relation.
 */
open class DirectedRelation<M : AbstractModel<M>, O : AbstractModel<O>>(

    /**
     * The query builder of the base model.
     */
    builder: QueryBuilder<M>,

    /**
     * The related model.
     */
    override val related: Int,

    override val relatedModelClass: KClass<O>
) : Relation<M, O>(builder, related, relatedModelClass) {

    /**
     * The related model.
     */
    open fun getRelated(): O? {
        return AbstractModel.loadFromId(
            relatedModelClass, related
        )
    }
}
