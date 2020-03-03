package com.yazan98.domain.models

import androidx.lifecycle.viewModelScope
import com.yazan98.data.ReposComponentImpl
import com.yazan98.data.repos.HomeRepository
import com.yazan98.domain.actions.StartsAction
import com.yazan98.domain.state.HomeState
import com.yazan98.domain.state.StartsState
import io.vortex.android.reducer.VortexViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StarsViewModel @Inject constructor() : VortexViewModel<StartsState, StartsAction>() {

    private val homeRepository: HomeRepository by lazy {
        ReposComponentImpl().getHomeRepository()
    }

    override suspend fun execute(newAction: StartsAction) {
        withContext(Dispatchers.IO) {
            acceptLoadingState(true)
            addRxRequest(homeRepository.getStarredRepositories().subscribe({
                viewModelScope.launch {
                    handleStateWithLoading(StartsState.StarsResponseState(it))
                }
            }, {
                it.message?.let {
                    viewModelScope.launch {
                        acceptLoadingState(false)
                        acceptNewState(StartsState.ErrorState(it))
                    }
                }
            }))
        }
    }

    override suspend fun getInitialState(): StartsState {
        return StartsState.EmptyState()
    }

}
