package com.yazan98.autohub.fragments.repo

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.yazan98.autohub.R
import com.yazan98.autohub.adapters.ContributersAdapter
import com.yazan98.data.models.GithubUser
import com.yazan98.domain.actions.ContributerAction
import com.yazan98.domain.models.ContrbutersViewModel
import com.yazan98.domain.state.ContributerState
import io.vortex.android.ui.fragment.VortexFragment
import io.vortex.android.utils.random.VortexRecyclerViewDecoration
import io.vortex.android.utils.ui.goneView
import io.vortex.android.utils.ui.linearVerticalLayout
import io.vortex.android.utils.ui.showView
import kotlinx.android.synthetic.main.fragment_contributers.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContributorsFragment @Inject constructor() : VortexFragment<ContributerState, ContributerAction, ContrbutersViewModel>() {

    private val viewModel: ContrbutersViewModel by viewModels()
    override fun getController(): ContrbutersViewModel = viewModel

    override fun initScreen(view: View) {
        lifecycleScope.launch {
            if (getController().getStateHandler().value == null) {
                getController().execute(ContributerAction.GetContributersByRepoInfo())
            }
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_contributers
    }

    override suspend fun getLoadingState(newState: Boolean) {
        withContext(Dispatchers.Main) {
            when (newState) {
                true -> {
                    ContributersRecycler?.goneView()
                    ContributersProgress?.showView()
                }

                false -> {
                    ContributersRecycler?.showView()
                    ContributersProgress?.goneView()
                }
            }
        }
    }

    override suspend fun onStateChanged(newState: ContributerState) {
        withContext(Dispatchers.IO) {
            when (newState) {
                is ContributerState.ErrorState -> messageController.showSnackbarWithColor(activity, newState.get(), R.color.colorPrimary)
                is ContributerState.SuccessState -> showContributersList(newState.get())
            }
        }
    }

    private suspend fun showContributersList(response: List<GithubUser>) {
        withContext(Dispatchers.Main) {
            activity?.let {
                ContributersRecycler?.apply {
                    this.linearVerticalLayout(it)
                    this.adapter = ContributersAdapter(response)
                    (this.adapter as ContributersAdapter).context = it
                    this.addItemDecoration(VortexRecyclerViewDecoration(it, LinearLayoutManager.HORIZONTAL, 5))
                }
            }
        }
    }
}