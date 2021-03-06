package com.yazan98.domain.models

import androidx.lifecycle.viewModelScope
import com.yazan98.data.ApplicationPrefs
import com.yazan98.data.ReposComponentImpl
import com.yazan98.data.models.internal.LoginInfo
import com.yazan98.data.models.ProfileResponse
import com.yazan98.data.repos.HomeRepository
import com.yazan98.domain.actions.ProfileAction
import com.yazan98.domain.state.ProfileState
import io.vortex.android.reducer.VortexViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel: VortexViewModel<ProfileState, ProfileAction>() {

    private val profileResponse = ProfileResponse()
    private val homeRepository: HomeRepository by lazy {
        ReposComponentImpl().getHomeRepository()
    }

    override suspend fun execute(newAction: ProfileAction) {
        withContext(Dispatchers.IO) {
            if (getStateHandler().value == null || getStateHandler().value is ProfileState.ErrorResponse) {
                when (newAction) {
                    is ProfileAction.GetProfileInfoAction -> getProfileInfo()
                    is ProfileAction.GetRepositoriesAction -> getRepositories()
                    is ProfileAction.GetOrganizationsAction -> getOrgs()
                    is ProfileAction.LoginAccountInfoAction -> loginAccount(newAction.get())
                }
            }
        }
    }

    private suspend fun getRepositories() {
        withContext(Dispatchers.IO) {
            addRxRequest(homeRepository.getServiceProvider().getRepositories().subscribe({
                viewModelScope.launch {
                    profileResponse.repositories = it
                }
            }, {
                it.message?.let {
                    viewModelScope.launch {
                        acceptLoadingState(false)
                        acceptNewState(ProfileState.ErrorResponse(it))
                    }
                }
            }))
        }
    }

    private suspend fun getProfileInfo() {
        withContext(Dispatchers.IO) {
            acceptLoadingState(true)
            addRxRequest(homeRepository.getServiceProvider().getProfileInfo().subscribe({
                profileResponse.profile = it
            }, {
                it.message?.let {
                    viewModelScope.launch {
                        acceptLoadingState(false)
                        acceptNewState(ProfileState.ErrorResponse(it))
                    }
                }
            }))
        }
    }

    private suspend fun loginAccount(loginInfo: LoginInfo) {
        withContext(Dispatchers.IO) {
            ApplicationPrefs.saveUsername(loginInfo.username)
            ApplicationPrefs.savePassword(loginInfo.password)
            acceptLoadingState(true)
            addRxRequest(homeRepository.getServiceProvider().getProfileInfo().subscribe({
               profileResponse.profile = it
            }, {
                it.message?.let {
                    viewModelScope.launch {
                        acceptLoadingState(false)
                        acceptNewState(ProfileState.ErrorResponse(it))
                    }
                }
            }))
        }
    }

    private suspend fun getOrgs() {
        withContext(Dispatchers.IO) {
            addRxRequest(homeRepository.getOrgs().subscribe({
                viewModelScope.launch {
                    profileResponse.organizations = it
                    handleStateWithLoading(ProfileState.SuccessState(profileResponse))
                }
            }, {
                it.message?.let {
                    viewModelScope.launch {
                        acceptLoadingState(false)
                        acceptNewState(ProfileState.ErrorResponse(it))
                    }
                }
            }))
        }
    }

    override suspend fun getInitialState(): ProfileState {
        return ProfileState.EmptyState()
    }

}