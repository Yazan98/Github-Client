package com.yazan98.autohub.fragments.repo

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.yazan98.autohub.R
import com.yazan98.autohub.adapters.CommitAdapter
import com.yazan98.data.ApplicationPrefs
import com.yazan98.data.models.GithubCommit
import com.yazan98.data.models.internal.RepoInfo
import com.yazan98.domain.actions.CommitAction
import com.yazan98.domain.actions.RepositoryAction
import com.yazan98.domain.models.CommitsViewModel
import com.yazan98.domain.state.CommitState
import io.vortex.android.ui.fragment.VortexFragment
import io.vortex.android.utils.random.VortexRecyclerViewDecoration
import io.vortex.android.utils.ui.goneView
import io.vortex.android.utils.ui.linearVerticalLayout
import io.vortex.android.utils.ui.showView
import kotlinx.android.synthetic.main.fragment_commits.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CommitsFragment @Inject constructor() : VortexFragment<CommitState, CommitAction, CommitsViewModel>() {

    private val viewModel: CommitsViewModel by viewModels()
    override fun getController(): CommitsViewModel {
        return viewModel
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_commits
    }

    override suspend fun getLoadingState(newState: Boolean) {
        withContext(Dispatchers.Main) {
            when (newState) {
                true -> {
                    CommitsLoading?.showView()
                    CommitsRecycler?.goneView()
                }

                false -> {
                    CommitsLoading?.goneView()
                    CommitsRecycler?.showView()
                }
            }
        }
    }

    override fun initScreen(view: View) {
        lifecycleScope.launch {
            lifecycleScope.launch {
                getController().execute(
                    CommitAction.GetCommitsByRepoInfo(
                        RepoInfo(
                            ApplicationPrefs.getSelectedUsername(),
                            ApplicationPrefs.getSelectedRepo()
                        )
                    )
                )
            }
        }
    }

    override suspend fun onStateChanged(newState: CommitState) {
        withContext(Dispatchers.IO) {
            when (newState) {
                is CommitState.ErrorState -> messageController.showSnackbarWithColor(activity, newState.get(), R.color.colorPrimary)
                is CommitState.SuccessState -> showCommits(newState.get())
            }
        }
    }

    private suspend fun showCommits(response: List<GithubCommit>) {
        withContext(Dispatchers.Main) {
            activity?.let {
                CommitsRecycler?.apply {
                    this.linearVerticalLayout(it)
                    this.adapter = CommitAdapter(response)
                    (this.adapter as CommitAdapter).context = it
                    this.addItemDecoration(VortexRecyclerViewDecoration(it, LinearLayoutManager.VERTICAL, 5))
                }
            }
        }
    }
}