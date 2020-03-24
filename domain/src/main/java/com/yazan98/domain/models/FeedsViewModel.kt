package com.yazan98.domain.models

import androidx.lifecycle.viewModelScope
import com.yazan98.data.ApplicationPrefs
import com.yazan98.data.ReposComponentImpl
import com.yazan98.data.repos.HomeRepository
import com.yazan98.domain.actions.FeedsAction
import com.yazan98.domain.state.FeedsState
import io.vortex.android.reducer.VortexViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FeedsViewModel @Inject constructor() : VortexViewModel<FeedsState, FeedsAction>() {

    private val repository: HomeRepository by lazy {
        ReposComponentImpl().getHomeRepository()
    }

    override suspend fun execute(newAction: FeedsAction) {
        withContext(Dispatchers.IO) {
            when (newAction) {
                is FeedsAction.GetFeedsByUsername -> getFeedsByUsername()
            }
        }
    }

    private suspend fun getFeedsByUsername() {
        withContext(Dispatchers.IO) {
            acceptLoadingState(true)
            addRxRequest(repository.getFeedsList(ApplicationPrefs.getUsername()).subscribe({
                viewModelScope.launch {
                    handleStateWithLoading(FeedsState.SuccessState(it))
                }
            }, {
                viewModelScope.launch {
                    it.message?.let {
                        acceptLoadingState(false)
                        acceptNewState(FeedsState.ErrorState(it))
                    }
                }
            }))
        }
    }

    override suspend fun getInitialState(): FeedsState {
        return FeedsState.EmptyState()
    }

}
