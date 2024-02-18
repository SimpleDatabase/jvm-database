package de.plk.database.model.relation

import de.plk.database.model.AbstractModel
import de.plk.database.sql.build.QueryBuilder

/**
 * Represents the abstract relation.
 */
open class Relation<M : AbstractModel<M>>(

    /**
     * The query builder of the base model.
     */
    val builder: QueryBuilder<M>
) {

    lateinit var related: AbstractModel<M>



}