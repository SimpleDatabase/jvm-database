package de.plk.database.sql.relation

import de.plk.database.model.AbstractModel
import de.plk.database.model.privot.PivotModel
import de.plk.database.model.relation.many.BelongsToMany
import de.plk.database.model.relation.many.ToPivot
import de.plk.database.model.relation.one.BelongsTo
import kotlin.reflect.KClass

/**
 * Represents the builder to add pivot relations to a model.
 */
interface PivotBuilder<M : AbstractModel<M>> {

    /**
     * Creates a pivot relation.
     *
     * @param model The related model.
     * @param pivotModel The pivot model base.
     *
     * @return The pivot related model.
     */
    fun <P : AbstractModel<P>, O : AbstractModel<O>> toPivot(model: KClass<O>, pivotModel: PivotModel<P, M, O>): ToPivot<P, M, O>


}