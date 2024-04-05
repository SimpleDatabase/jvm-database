package de.plk.database.model.relation.many

import de.plk.database.model.AbstractModel
import de.plk.database.model.relation.DirectedRelation
import de.plk.database.sql.build.QueryBuilder

/**
 * Represents the belongs to many relation.
 */
class BelongsToMany<M : AbstractModel<M>, O : AbstractModel<O>>(

    /**
     * The query builder of the base model.
     */
    builder: QueryBuilder<M>,
    related: O,
) : DirectedRelation<M, O>(builder, related)