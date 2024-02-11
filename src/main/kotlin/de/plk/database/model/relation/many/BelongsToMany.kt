package de.plk.database.model.relation.many

import de.plk.database.model.relation.Relation
import de.plk.database.sql.QueryBuilder

/**
 * @author SoftwareBuilds
 * @since 10.02.2024 11:40
 * Copyright © 2024 | SoftwareBuilds | All rights reserved.
 */
class BelongsToMany(
    builder: QueryBuilder
) : Relation(builder)