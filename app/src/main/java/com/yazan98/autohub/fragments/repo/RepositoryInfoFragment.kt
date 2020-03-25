package com.yazan98.autohub.fragments.repo

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.yazan98.autohub.R
import com.yazan98.data.ApplicationPrefs
import com.yazan98.data.models.GithubRepositoryModel
import com.yazan98.data.models.internal.RepoInfo
import com.yazan98.domain.actions.RepositoryAction
import com.yazan98.domain.models.RepositoryViewModel
import com.yazan98.domain.state.RepositoryState
import io.vortex.android.ui.VortexErrorType
import io.vortex.android.ui.fragment.VortexFragment
import io.vortex.android.utils.random.VortexImageLoaders
import io.vortex.android.utils.ui.goneView
import io.vortex.android.utils.ui.showView
import kotlinx.android.synthetic.main.fragment_repository_info.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryInfoFragment @Inject constructor() :
    VortexFragment<RepositoryState, RepositoryAction, RepositoryViewModel>() {

    private val viewModel: RepositoryViewModel by viewModels()
    override suspend fun getController(): RepositoryViewModel {
        return viewModel
    }

    override fun initScreen(view: View) {
        lifecycleScope.launch {
            getController().execute(
                RepositoryAction.GetRepoInfo(
                    RepoInfo(
                        ApplicationPrefs.getSelectedUsername(),
                        ApplicationPrefs.getSelectedRepo()
                    )
                )
            )
        }
    }

    override suspend fun getLoadingState(newState: Boolean) {
        withContext(Dispatchers.Main) {
            when (newState) {
                true -> {
                    RepoInfoProgress?.showView()
                    RepoInfoContainer?.goneView()
                }

                false -> {
                    RepoInfoProgress?.goneView()
                    RepoInfoContainer?.showView()
                }
            }
        }
    }

    override suspend fun onStateChanged(newState: RepositoryState) {
        withContext(Dispatchers.IO) {
            when (newState) {
                is RepositoryState.ErrorState -> showError(
                    newState.get(),
                    VortexErrorType.SHORT_TOAST
                )
                is RepositoryState.SuccessRepositoryState -> showRepoInfo(newState.get())
            }
        }
    }

    private suspend fun showRepoInfo(repo: GithubRepositoryModel) {
        withContext(Dispatchers.Main) {
            RepositoryOwnerImageRepo?.apply {
                VortexImageLoaders.loadLargeImageWithFresco(repo.owner.avatar_url, this, 700, 700)
            }

            RepoNameRepository?.let {
                it.text = repo.full_name
            }

            RepoSubName?.let {
                it.text = repo.name
            }

            RepoDescription?.let {
                it.text = repo.description
            }

            StarsNumber?.let {
                it.text = repo.watchers_count.toString()
            }

            ForksNumber?.let {
                it.text = repo.forks.toString()
            }

            ProblemsCount?.let {
                it.text = repo.open_issues_count.toString()
            }
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_repository_info
    }

}
