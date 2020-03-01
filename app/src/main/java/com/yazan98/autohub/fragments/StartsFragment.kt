package com.yazan98.autohub.fragments

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.yazan98.autohub.R
import com.yazan98.autohub.adapters.RepositoryAdapter
import com.yazan98.data.models.GithubRepositoryModel
import com.yazan98.domain.actions.StartsAction
import com.yazan98.domain.models.StarsViewModel
import com.yazan98.domain.state.StartsState
import io.vortex.android.ui.VortexErrorType
import io.vortex.android.ui.fragment.VortexFragment
import io.vortex.android.utils.random.VortexRecyclerViewDecoration
import io.vortex.android.utils.ui.goneView
import io.vortex.android.utils.ui.linearVerticalLayout
import io.vortex.android.utils.ui.showView
import kotlinx.android.synthetic.main.fragment_starred_repos.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StartsFragment @Inject constructor() : VortexFragment<StartsState, StartsAction, StarsViewModel>() {

    private val viewModel: StarsViewModel by viewModels()

    override suspend fun getController(): StarsViewModel {
        return viewModel
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_starred_repos
    }

    override fun initScreen(view: View) {
        lifecycleScope.launch {
            getController().execute(StartsAction.GetStarsForUser())
        }
    }

    override suspend fun getLoadingState(newState: Boolean) {
        withContext(Dispatchers.Main) {
            when (newState) {
                true -> {
                    StarredRecycler?.goneView()
                    StarredProgress?.showView()
                }

                false -> {
                    StarredRecycler?.showView()
                    StarredProgress?.goneView()
                }
            }
        }
    }

    override suspend fun onStateChanged(newState: StartsState) {
        withContext(Dispatchers.IO) {
            when (newState) {
                is StartsState.StarsResponseState -> showStarredRepositories(newState.get())
                is StartsState.ErrorState -> showError(newState.get(), VortexErrorType.SNACK_BAR)
            }
        }
    }

    private suspend fun showStarredRepositories(response: List<GithubRepositoryModel>) {
        withContext(Dispatchers.Main) {
            activity?.let {
                StarredRecycler?.apply {
                    this.linearVerticalLayout(it)
                    this.adapter = RepositoryAdapter(response)
                    (this.adapter as RepositoryAdapter).context = it
                    this.addItemDecoration(VortexRecyclerViewDecoration(it, LinearLayoutManager.VERTICAL, 5))
                }
            }
        }
    }

}
