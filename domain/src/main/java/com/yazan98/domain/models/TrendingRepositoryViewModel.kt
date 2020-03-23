package com.yazan98.domain.models

import androidx.lifecycle.viewModelScope
import com.yazan98.data.ReposComponentImpl
import com.yazan98.data.repos.TrendingRepository
import com.yazan98.domain.actions.TrendingRepositoryAction
import com.yazan98.domain.state.StartsState
import com.yazan98.domain.state.TredingRepositoryState
import io.vortex.android.reducer.VortexViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TrendingRepositoryViewModel @Inject constructor() : VortexViewModel<TredingRepositoryState, TrendingRepositoryAction>() {

    private val repository: TrendingRepository by lazy {
        ReposComponentImpl().getTrendingRepository()
    }

    override suspend fun execute(newAction: TrendingRepositoryAction) {
        withContext(Dispatchers.IO) {
            when (newAction) {
                is TrendingRepositoryAction.GetTrendingDailyRepositories -> getTredingRepos("daily")
                is TrendingRepositoryAction.GetTrendingMonthlyRepositories -> getTredingRepos("monthly")
                is TrendingRepositoryAction.GetTrendingWeeklyRepositories -> getTredingRepos("weekly")
            }
        }
    }

    private suspend fun getTredingRepos(type: String) {
        withContext(Dispatchers.IO) {
            acceptLoadingState(true)
            addRxRequest(repository.getDailyTrending(type).subscribe({
                viewModelScope.launch {
                    handleStateWithLoading(TredingRepositoryState.SuccessState(it))
                }
            }, {
                it.message?.let {
                    viewModelScope.launch {
                        acceptLoadingState(false)
                        acceptNewState(TredingRepositoryState.ErrorState(it))
                    }
                }
            }))
        }
    }

    override suspend fun getInitialState(): TredingRepositoryState {
        return TredingRepositoryState.EmptyState()
    }

}
