package de.plk.database.model.privot

import de.plk.database.model.AbstractModel

/**
 * Defines that any subclass is a database model.
 */
abstract class PivotModel<P : AbstractModel<P>, M : AbstractModel<M>, O : AbstractModel<O>> : AbstractModel<P>() {


}