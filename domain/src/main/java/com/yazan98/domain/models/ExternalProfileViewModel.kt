package com.yazan98.domain.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yazan98.data.ApplicationPrefs
import com.yazan98.data.ReposComponentImpl
import com.yazan98.data.database.models.GithubAccount
import com.yazan98.data.models.GithubRepositoryModel
import com.yazan98.data.repos.HomeRepository
import com.yazan98.data.repos.ProfileRepository
import com.yazan98.domain.state.ExternalProfileState
import io.vortex.android.reducer.VortexPureViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExternalProfileViewModel @Inject constructor() : VortexPureViewModel() {

//    val accountInfo: MutableLiveData<GithubAccount> by lazy {  MutableLiveData<GithubAccount>() }
//    val reposInfo:  MutableLiveData<GithubRepositoryModel> by lazy { MutableLiveData<GithubRepositoryModel>() }
//    private val profileRepo: ProfileRepository by lazy {
//        ReposComponentImpl().getProfileRepository()
//    }
//
//    companion object {
//        const val ACCOUNT_NAME_ACTION = 0
//        const val ACCOUNT_REPOS_ACTION = 1
//        const val ACCOUNT_ORGS_ACTION = 2
//    }
//
//    suspend fun execute(newAction: Any) {
//        withContext(Dispatchers.IO) {
//            if (getStateHandler().value == null) {
//                when (newAction) {
//                    ACCOUNT_NAME_ACTION -> getAccountInformation()
//                    ACCOUNT_REPOS_ACTION -> getReposByAccountName()
//                    ACCOUNT_ORGS_ACTION -> getOrgsByAccountName()
//                }
//            }
//        }
//    }
//
//    private suspend fun getAccountInformation() {
//        withContext(Dispatchers.IO) {
//            acceptNewState(ExternalProfileState.LoadingState())
//            addRxRequest(profileRepo.getProfileInfoByUsername(ApplicationPrefs.getSelectedUsername()).subscribe({
//                viewModelScope.launch {
//                    acceptNewState(ExternalProfileState.SuccessState(it))
//                }
//            }, {
//                viewModelScope.launch {
//                    it.message?.let {
//                        acceptNewState(ExternalProfileState.ErrorState(it))
//                    }
//                }
//            }))
//        }
//    }

}