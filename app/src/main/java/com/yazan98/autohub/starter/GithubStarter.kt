package com.yazan98.autohub.starter

import com.yazan98.data.database.mappers.DatabaseAccountMapper
import com.yazan98.data.database.models.GithubAccount
import com.yazan98.data.database.repos.DatabaseFollowingRepository
import com.yazan98.data.models.GithubUser
import com.yazan98.data.repos.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

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
//        withContext(Dispatchers.Main) {
//            profileRepository.getFollowingByToken().subscribe({
//                it.forEach {
//                    println("The Entity Response : $it")
//                }
//                DatabaseFollowingRepository().apply {
//                    this.sageEntities(getLocalAccountsByResponse(it))
//                }
//            }, {
//                it.printStackTrace()
//                it.message?.let {
//                    Timber.d("The Response : Error : $it")
//                }
//            })
//        }
    }

    private fun getLocalAccountsByResponse(list: List<GithubUser>): List<GithubAccount> {
        val response = ArrayList<GithubAccount>()
        DatabaseAccountMapper().apply {
            list.forEach {
                response.add(this.get(it))
            }
        }
        println("The Mapper Result : ${response}")
        return response
    }

    override suspend fun startSaveFollowers() {
//        withContext(Dispatchers.Main) {
//            profileRepository.getFollowersByToken().subscribe({
//                it.forEach {
//                    println("The Entity Response : $it")
//                }
//                DatabaseFollowingRepository().apply {
//                    this.sageEntities(getLocalAccountsByResponse(it))
//                }
//            }, {
//                it.printStackTrace()
//                it.message?.let {
//                    Timber.d("The Response : Error : $it")
//                }
//            })
//        }
    }

    override suspend fun startSaveProfileInfo() {
        withContext(Dispatchers.Main) {

        }
    }

}
