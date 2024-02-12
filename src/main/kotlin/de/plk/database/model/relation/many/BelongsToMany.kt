package de.plk.database.model.relation.many

import de.plk.database.model.relation.Relation
import de.plk.database.sql.QueryBuilder

/**
 * Represents the belongs to many relation.
 */
class BelongsToMany(

    /**
     * The query builder of the base model.
     */
    builder: QueryBuilder
) : Relation(builder)