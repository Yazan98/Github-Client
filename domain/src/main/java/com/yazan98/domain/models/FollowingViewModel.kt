package com.yazan98.domain.models

import androidx.lifecycle.MutableLiveData
import com.yazan98.data.database.mappers.DatabaseAccountMapper
import com.yazan98.data.database.models.GithubAccount
import com.yazan98.data.database.repos.DatabaseFollowersRepository
import com.yazan98.data.database.repos.DatabaseFollowingRepository
import com.yazan98.data.repos.ProfileRepository
import com.yazan98.domain.actions.FollowingAction
import com.yazan98.domain.state.FollowingState
import io.realm.OrderedRealmCollection
import io.vortex.android.reducer.VortexViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FollowingViewModel @Inject constructor(): VortexViewModel<FollowingState, FollowingAction>() {

    val followersObserver: MutableLiveData<FollowingState> by lazy {
        MutableLiveData<FollowingState>()
    }

    val followingObserver: MutableLiveData<FollowingState> by lazy {
        MutableLiveData<FollowingState>()
    }

    override suspend fun getInitialState(): FollowingState {
        return FollowingState.EmptyState()
    }

    override suspend fun execute(newAction: FollowingAction) {
        withContext(Dispatchers.IO) {
            when (newAction) {
                is FollowingAction.GetFollowersUsersAction -> {
                    getFollowers()
                }

                is FollowingAction.GetFollowingUsersAction -> {
                    getFollowing()
                }
            }
        }
    }

    private suspend fun getFollowers() {
        withContext(Dispatchers.Main) {
            DatabaseFollowersRepository().apply {
                this.getEntitiesBySpecialType().let {
                    followersObserver.postValue(FollowingState.FollowingUsersState(it))
                }
            }
        }
    }

    private suspend fun getFollowing() {
        withContext(Dispatchers.Main) {
            DatabaseFollowingRepository().apply {
                this.getEntitiesBySpecialType().let {
                    followingObserver.postValue(FollowingState.FollowingUsersState(it))
                }
            }
        }
    }

}
