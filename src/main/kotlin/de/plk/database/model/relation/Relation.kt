package de.plk.database.model.relation

import de.plk.database.sql.QueryBuilder

/**
 * Represents the abstract relation.
 */
open class Relation(

    /**
     * The query builder of the base model.
     */
    val builder: QueryBuilder
)