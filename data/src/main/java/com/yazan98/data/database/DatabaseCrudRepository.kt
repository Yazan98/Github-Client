package com.yazan98.data.database

import io.realm.RealmResults

interface DatabaseCrudRepository<T> {

    suspend fun saveEntities(list: List<T>)

    suspend fun getAllEntities(): RealmResults<T>

    suspend fun getEntitiesBySpecialType(): RealmResults<T>

}
