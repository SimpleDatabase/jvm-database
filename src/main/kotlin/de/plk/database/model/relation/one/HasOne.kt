package de.plk.database.model.relation.one

import de.plk.database.model.AbstractModel
import de.plk.database.model.relation.DirectedRelation
import de.plk.database.sql.build.QueryBuilder

/**
 * Represents the has one relation.
 */
class HasOne<M : AbstractModel<M>, O : AbstractModel<O>> (

    /**
     * The query builder of the base model.
     */
    builder: QueryBuilder<M>,

    /**
     * The related model.
     */
    related: O
) : DirectedRelation<M, O>(builder, related)
