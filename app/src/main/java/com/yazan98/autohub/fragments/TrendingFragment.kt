package com.yazan98.autohub.fragments

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.yazan98.autohub.R
import com.yazan98.autohub.adapters.TrendingAdapter
import com.yazan98.autohub.adapters.TrendingRepoListener
import com.yazan98.autohub.dialogs.TrendingFilterDialog
import com.yazan98.autohub.screen.RepositoryScreen
import com.yazan98.data.ApplicationPrefs
import com.yazan98.data.models.TrendingRepo
import com.yazan98.domain.actions.TrendingRepositoryAction
import com.yazan98.domain.models.TrendingRepositoryViewModel
import com.yazan98.domain.state.TredingRepositoryState
import io.vortex.android.ui.fragment.VortexFragment
import io.vortex.android.utils.random.VortexRecyclerViewDecoration
import io.vortex.android.utils.ui.goneView
import io.vortex.android.utils.ui.linearVerticalLayout
import io.vortex.android.utils.ui.showView
import kotlinx.android.synthetic.main.fragment_trending.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TrendingFragment @Inject constructor() :
    VortexFragment<TredingRepositoryState, TrendingRepositoryAction, TrendingRepositoryViewModel>(),
    TrendingFilterDialog.FilterListener {

    private val viewModel: TrendingRepositoryViewModel by viewModels()
    private val trendingFilterDialog: TrendingFilterDialog by lazy {
        TrendingFilterDialog()
    }

    override suspend fun getController(): TrendingRepositoryViewModel {
        return viewModel
    }

    override fun initScreen(view: View) {

        trendingFilterDialog.attachListener(this)
        lifecycleScope.launch {
            if (getController().getStateHandler().value == null) {
                getController().execute(TrendingRepositoryAction.GetTrendingDailyRepositories())
            }
        }

        TrendingFilterButton?.apply {
            this.setOnClickListener {
                activity?.let {
                    it.supportFragmentManager?.let {
                        trendingFilterDialog.show(it, "")
                    }
                }
            }
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_trending
    }

    override suspend fun getLoadingState(newState: Boolean) {
        withContext(Dispatchers.Main) {
            when (newState) {
                true -> {
                    LoadingTrending?.showView()
                    TrendingRecycler?.goneView()
                }

                false -> {
                    LoadingTrending?.goneView()
                    TrendingRecycler?.showView()
                }
            }
        }
    }

    override suspend fun onStateChanged(newState: TredingRepositoryState) {
        withContext(Dispatchers.IO) {
            when (newState) {
                is TredingRepositoryState.ErrorState -> messageController.showSnackbarWithColor(activity, newState.get(), R.color.colorPrimary)
                is TredingRepositoryState.SuccessState -> showTrendingRepos(newState.get())
            }
        }
    }

    private suspend fun showTrendingRepos(result: List<TrendingRepo>) {
        withContext(Dispatchers.Main) {
            activity?.let {
                TrendingRecycler?.apply {
                    this.linearVerticalLayout(it)
                    this.adapter = TrendingAdapter(result, repoClicked)
                    (this.adapter as TrendingAdapter).context = it
                    this.addItemDecoration(VortexRecyclerViewDecoration(it, LinearLayoutManager.VERTICAL, 5))
                }
            }
        }
    }

    private val repoClicked = object : TrendingRepoListener {
        override fun onRepoClicked(repo: TrendingRepo) {
            lifecycleScope.launch {
                ApplicationPrefs.saveSelectedRepo(repo.author)
                ApplicationPrefs.saveSelectedUsername(repo.name)
                startScreen<RepositoryScreen>(false)
            }
        }
    }

    override fun onTimeFilter(type: String) {
        lifecycleScope.launch {
            when (type) {
                "daily" -> getController().execute(TrendingRepositoryAction.GetTrendingDailyRepositories())
                "weekly" -> getController().execute(TrendingRepositoryAction.GetTrendingWeeklyRepositories())
                "monthly" -> getController().execute(TrendingRepositoryAction.GetTrendingMonthlyRepositories())
            }
        }
    }

}