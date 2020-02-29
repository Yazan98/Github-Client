package com.yazan98.autohub.starter

import com.yazan98.data.database.mappers.DatabaseAccountMapper
import com.yazan98.data.database.models.GithubAccount
import com.yazan98.data.database.repos.DatabaseFollowingRepository
import com.yazan98.data.models.GithubUser
import com.yazan98.data.repos.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubStarter(private val username: String): GithubStarterImpl {

    private val profileRepository: ProfileRepository by lazy {
        ProfileRepository()
    }

    override suspend fun startActions() {
        startSaveFollowers()
        startSaveFollowings()
        startSaveProfileInfo()
    }

    override suspend fun startSaveFollowings() {
        withContext(Dispatchers.Main) {
            profileRepository.getFollowingByToken(username).subscribe {
                DatabaseFollowingRepository().apply {
                    this.sageEntities(getLocalAccountsByResponse(it))
                }
            }
        }
    }

    private fun getLocalAccountsByResponse(list: List<GithubUser>): List<GithubAccount> {
        val response = ArrayList<GithubAccount>()
        DatabaseAccountMapper().apply {
            list.forEach {
                response.add(this.get(it))
            }
        }
        return response
    }


    override suspend fun startSaveFollowers() {
        withContext(Dispatchers.Main) {

        }
    }

    override suspend fun startSaveProfileInfo() {
        withContext(Dispatchers.Main) {

        }
    }

}
