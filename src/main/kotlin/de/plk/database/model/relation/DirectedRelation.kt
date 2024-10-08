package de.plk.database.model.relation

import de.plk.database.model.AbstractModel
import de.plk.database.sql.build.QueryBuilder

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
    related: O
) : Relation<M, O>(builder, related)
