package de.plk.database.model.relation.many

import de.plk.database.model.AbstractModel
import de.plk.database.model.relation.Relation
import de.plk.database.sql.build.QueryBuilder

/**
 * Represents the belongs to many relation.
 */
class BelongsToMany<M : AbstractModel<M>, O : AbstractModel<O>>(

    /**
     * The query builder of the base model.
     */
    builder: QueryBuilder<M>,
    related: O
) : Relation<M, O>(builder, related)