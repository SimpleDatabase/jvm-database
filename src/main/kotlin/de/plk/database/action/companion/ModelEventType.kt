package de.plk.database.action.companion

import de.plk.database.model.AbstractModel
import de.plk.database.model.event.EventClosure

/**
 * @author SoftwareBuilds
 * @since 10.02.2024 01:12
 * Copyright © 2024 | SoftwareBuilds | All rights reserved.
 */

enum class ModelEventType {

    RETRIEVED,
    CREATING,
    CREATED,
    UPDATING,
    UPDATED,
    SAVING,
    SAVED,
    DELETING,
    DELETED

}