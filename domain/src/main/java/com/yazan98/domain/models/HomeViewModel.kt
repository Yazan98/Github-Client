package com.yazan98.domain.models

import androidx.lifecycle.viewModelScope
import com.yazan98.data.ReposComponent
import com.yazan98.data.ReposComponentImpl
import com.yazan98.data.repos.HomeRepository
import com.yazan98.domain.actions.HomeAction
import com.yazan98.domain.state.HomeState
import io.vortex.android.reducer.VortexViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeViewModel @Inject constructor() : VortexViewModel<HomeState, HomeAction>() {

    private val homeRepository: HomeRepository by lazy {
        ReposComponentImpl().getHomeRepository()
    }

    override suspend fun execute(newAction: HomeAction) {
        withContext(Dispatchers.IO) {
            when (newAction) {
                is HomeAction.GetNotificationsAction -> getAllNotifications()
            }
        }
    }

    private suspend fun getAllNotifications() {
        withContext(Dispatchers.IO) {
            acceptLoadingState(true)
            addRxRequest(homeRepository.getNotifications().subscribe({
                viewModelScope.launch {
                    handleStateWithLoading(HomeState.NotificationsState(it))
                }
            }, {
                it.message?.let {
                    viewModelScope.launch {
                        acceptLoadingState(false)
                        acceptNewState(HomeState.ErrorState(it))
                    }
                }
            }))
        }
    }

    override suspend fun getInitialState(): HomeState {
        return HomeState.EmptyState()
    }

    override fun onCleared() {
        super.onCleared()
        getRxRepository().clearRepository()
    }

}
