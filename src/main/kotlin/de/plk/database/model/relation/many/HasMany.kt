package de.plk.database.model.relation.many

import de.plk.database.model.relation.Relation
import de.plk.database.sql.QueryBuilder

/**
 * Represents the has many relation.
 */
class HasMany(

    /**
     * The query builder of the base model.
     */
    builder: QueryBuilder
) : Relation(builder)