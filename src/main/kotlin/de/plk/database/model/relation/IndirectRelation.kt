package de.plk.database.model.relation

import de.plk.database.model.AbstractModel
import de.plk.database.model.privot.PivotModel
import de.plk.database.sql.build.QueryBuilder

/**
 * Represents the abstract relation.
 */
open class IndirectRelation<P : AbstractModel<P>, M : AbstractModel<M>, O : AbstractModel<O>>(

    /**
     * The query builder of the base model.
     */
    builder: QueryBuilder<M>,

    relatedM: M,

    relatedO: O,

    pivotModel: PivotModel<P, M, O>
) : Relation<M, O>(builder, relatedO)