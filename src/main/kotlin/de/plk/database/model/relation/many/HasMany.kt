package de.plk.database.model.relation.many

import de.plk.database.model.AbstractModel
import de.plk.database.model.relation.Relation
import de.plk.database.sql.build.QueryBuilder

/**
 * Represents the has many relation.
 */
class HasMany<M : AbstractModel<M>>(

    /**
     * The query builder of the base model.
     */
    builder: QueryBuilder<M>
) : Relation<M>(builder)