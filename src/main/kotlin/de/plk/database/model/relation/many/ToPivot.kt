package de.plk.database.model.relation.many

import de.plk.database.model.AbstractModel
import de.plk.database.model.privot.PivotModel
import de.plk.database.model.relation.IndirectRelation
import de.plk.database.sql.build.QueryBuilder

/**
 * Represents the belongs to many relation.
 */
class ToPivot<P : AbstractModel<P>, M : AbstractModel<M>, O : AbstractModel<O>>(
    builder: QueryBuilder<M>,
    relatedM: M,
    relatedO: O,
    pivotModel: PivotModel<P, M, O>
) : IndirectRelation<P, M, O>(builder, relatedM, relatedO, pivotModel)