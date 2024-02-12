package de.plk.database.model.relation.one

import de.plk.database.model.relation.Relation
import de.plk.database.sql.QueryBuilder

/**
 * Represents the belongs to relation.
 */
class BelongsTo(

    /**
     * The query builder of the base model.
     */
    builder: QueryBuilder
) : Relation(builder)