package com.yazan98.autohub.fragments.repo

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.yazan98.autohub.R
import com.yazan98.data.models.GithubRepositoryModel
import com.yazan98.data.models.internal.RepoInfo
import com.yazan98.domain.actions.RepositoryAction
import com.yazan98.domain.models.RepositoryViewModel
import com.yazan98.domain.state.RepositoryState
import io.vortex.android.ui.VortexErrorType
import io.vortex.android.ui.fragment.VortexFragment
import kotlinx.android.synthetic.main.fragment_repository.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RepositoryFragment : VortexFragment<RepositoryState, RepositoryAction, RepositoryViewModel>() {

    private val viewModel: RepositoryViewModel by viewModels()
    override suspend fun getController(): RepositoryViewModel {
        return viewModel
    }

    override fun initScreen(view: View) {
        lifecycleScope.launch {
            arguments?.let { args ->
                args.getString("Username")?.also {
                    args.getString("RepoName")?.also { repoName ->
                        getController().execute(RepositoryAction.GetRepoInfo(RepoInfo(it, repoName)))
                    }
                }
            }
        }
    }

    override suspend fun getLoadingState(newState: Boolean) {
        withContext(Dispatchers.Main) {
            when(newState) {
                true -> {

                }

                false -> {

                }
            }
        }
    }

    override suspend fun onStateChanged(newState: RepositoryState) {
        withContext(Dispatchers.IO) {
            when (newState) {
                is RepositoryState.ErrorState -> showError(newState.get(), VortexErrorType.SHORT_TOAST)
                is RepositoryState.SuccessRepositoryState -> showRepoInfo(newState.get())
            }
        }
    }

    private suspend fun showRepoInfo(repo: GithubRepositoryModel) {
        withContext(Dispatchers.Main) {
            RepoNameField?.apply { this.text = repo.name }
            RepoName?.apply { this.text = repo.full_name }
            RepoBio?.apply { this.text = repo.description }
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_repository
    }

}
