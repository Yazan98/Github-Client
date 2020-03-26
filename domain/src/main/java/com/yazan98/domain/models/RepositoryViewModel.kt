package com.yazan98.domain.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yazan98.data.ReposComponentImpl
import com.yazan98.data.models.internal.RepoInfo
import com.yazan98.data.repos.RepoRepository
import com.yazan98.domain.actions.RepositoryAction
import com.yazan98.domain.state.RepositoryState
import io.vortex.android.reducer.VortexViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryViewModel @Inject constructor(): VortexViewModel<RepositoryState, RepositoryAction>() {

    private val repoInfo: MutableLiveData<RepoInfo> by lazy { MutableLiveData<RepoInfo>() }
    val topics: MutableLiveData<List<String>> by lazy { MutableLiveData<List<String>>() }
    val readmeFile: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    private val repo: RepoRepository by lazy {
        ReposComponentImpl().getRepositoryComponent()
    }

    override suspend fun execute(newAction: RepositoryAction) {
        when (newAction) {
            is RepositoryAction.GetRepoTopics -> getTopics()
            is RepositoryAction.GetRepoInfo -> {
                repoInfo.value = newAction.get()
                getRepoInfo(newAction.get())
            }
            is RepositoryAction.GetRepositoryReadme -> getRepoReadmeFile(newAction.get())
        }
    }

    private suspend fun getRepoReadmeFile(repoInfo: RepoInfo) {
        withContext(Dispatchers.IO) {
            addRxRequest(repo.getRepositoryReadmeFile(repoInfo.username, repoInfo.repoName).subscribe({
                println("The Url : ViewModel : ${it.html_url}")
                readmeFile.postValue(it.html_url)
            }, {
                it.message?.let {
                    viewModelScope.launch {
                        acceptNewState(RepositoryState.ErrorState(it))
                        acceptLoadingState(false)
                    }
                }
            }))
        }
    }

    private suspend fun getRepoInfo(repoInfo: RepoInfo) {
        withContext(Dispatchers.IO) {
            acceptLoadingState(true)
            addRxRequest(repo.getRepository(repoInfo.username, repoInfo.repoName).subscribe({
                viewModelScope.launch {
                    handleStateWithLoading(RepositoryState.SuccessRepositoryState(it))
                }
            }, {
                it.message?.let {
                    viewModelScope.launch {
                        acceptNewState(RepositoryState.ErrorState(it))
                        acceptLoadingState(false)
                    }
                }
            }))
        }
    }

    private suspend fun getTopics() {
        withContext(Dispatchers.IO) {
            repoInfo.value?.let {
                addRxRequest(repo.getRepositoryTopics(it.username, it.repoName).subscribe({
                    topics.postValue(it.names)
                }, {
                    it.message?.let {
                        viewModelScope.launch {
                            acceptNewState(RepositoryState.ErrorState(it))
                            acceptLoadingState(false)
                        }
                    }
                }))
            }
        }
    }

    override suspend fun getInitialState(): RepositoryState {
        return RepositoryState.EmptyState()
    }

}
