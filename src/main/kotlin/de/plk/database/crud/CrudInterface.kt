package de.plk.database.crud

import de.plk.database.model.AbstractModel

interface CrudInterface<K, M : AbstractModel> {

    fun create(model: M)

    fun read(key: K): M

    fun update(key: K, model: M)

    fun delete(key: K)

}