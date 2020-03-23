package com.yazan98.autohub.fragments

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.yazan98.autohub.R
import com.yazan98.autohub.adapters.TrendingAdapter
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
    VortexFragment<TredingRepositoryState, TrendingRepositoryAction, TrendingRepositoryViewModel>() {

    private val viewModel: TrendingRepositoryViewModel by viewModels()
    override suspend fun getController(): TrendingRepositoryViewModel {
        return viewModel
    }

    override fun initScreen(view: View) {
        lifecycleScope.launch {
            if (getController().getStateHandler().value == null) {
                getController().execute(TrendingRepositoryAction.GetTrendingDailyRepositories())
            }
        }

        TrendingFilterButton?.apply {
            this.setOnClickListener {

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
                    this.adapter = TrendingAdapter(result)
                    (this.adapter as TrendingAdapter).context = it
                    this.addItemDecoration(VortexRecyclerViewDecoration(it, LinearLayoutManager.VERTICAL, 5))
                }
            }
        }
    }

}