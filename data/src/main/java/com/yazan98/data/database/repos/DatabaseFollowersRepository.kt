package com.yazan98.data.database.repos

import com.yazan98.data.database.DatabaseCrudRepository
import com.yazan98.data.database.models.GithubAccount
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseFollowersRepository(private val database: Realm = Realm.getDefaultInstance()) : DatabaseCrudRepository<GithubAccount> {

    override suspend fun saveEntities(list: List<GithubAccount>) {
        withContext(Dispatchers.Main) {
            database.executeTransactionAsync {  internalDatabase ->
                list.forEach {
                    it.entityType = "Following"
                    internalDatabase.insertOrUpdate(it)
                }
            }
        }
    }

    override suspend fun getAllEntities(): RealmResults<GithubAccount> {
        return withContext(Dispatchers.Main) {
            database.where(GithubAccount::class.java).findAll()
        }
    }

    override suspend fun getEntitiesBySpecialType(): RealmResults<GithubAccount> {
        return withContext(Dispatchers.Main) {
            database.where(GithubAccount::class.java).equalTo("entityType", "Following").findAll()
        }
    }

}