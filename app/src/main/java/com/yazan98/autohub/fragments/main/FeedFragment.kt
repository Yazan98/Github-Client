package com.yazan98.autohub.fragments.main

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.yazan98.autohub.R
import com.yazan98.autohub.adapters.FeedsAdapter
import com.yazan98.autohub.adapters.listeners.FeedsListener
import com.yazan98.autohub.screen.RepositoryScreen
import com.yazan98.data.models.FeedResponse
import com.yazan98.domain.actions.FeedsAction
import com.yazan98.domain.models.FeedsViewModel
import com.yazan98.domain.state.FeedsState
import io.vortex.android.ui.fragment.VortexFragment
import io.vortex.android.utils.random.VortexRecyclerViewDecoration
import io.vortex.android.utils.ui.goneView
import io.vortex.android.utils.ui.linearVerticalLayout
import io.vortex.android.utils.ui.showView
import kotlinx.android.synthetic.main.fragment_feeds.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FeedFragment @Inject constructor() : VortexFragment<FeedsState, FeedsAction, FeedsViewModel>() {

    private val viewModel: FeedsViewModel by viewModels()
    private val pickerItems: List<String> by lazy { arrayListOf("View Profile", "View Repository") }

    override suspend fun getController(): FeedsViewModel = viewModel
    override fun getLayoutRes(): Int {
        return R.layout.fragment_feeds
    }

    override fun initScreen(view: View) {
        lifecycleScope.launch {
            getController().execute(FeedsAction.GetFeedsByUsername())
        }
    }

    override suspend fun getLoadingState(newState: Boolean) {
        withContext(Dispatchers.Main) {
            when (newState) {
                true -> {
                    FeedsProgress?.showView()
                    FeedsList?.goneView()
                }

                false -> {
                    FeedsProgress?.goneView()
                    FeedsList?.showView()
                }
            }
        }
    }

    override suspend fun onStateChanged(newState: FeedsState) {
        withContext(Dispatchers.IO) {
            when (newState) {
                is FeedsState.ErrorState -> messageController.showSnackbarWithColor(activity, newState.get(), R.color.colorPrimary)
                is FeedsState.SuccessState -> showFeedsList(newState.get())
            }
        }
    }

    private suspend fun showFeedsList(feeds: List<FeedResponse>) {
        withContext(Dispatchers.Main) {
            activity?.let {
                FeedsList?.apply {
                    this.linearVerticalLayout(it)
                    this.adapter = FeedsAdapter(feeds, itemListener)
                    (this.adapter as FeedsAdapter).context = it
                    this.addItemDecoration(VortexRecyclerViewDecoration(it, LinearLayoutManager.VERTICAL, 5))
                }
            }
        }
    }

    private val itemListener = object : FeedsListener {
        override fun onFeedClicked(item: FeedResponse) {
            activity?.let {
                MaterialDialog(it).show {
                    title(R.string.pick_method)
                    listItems(items = pickerItems) { dialog, index, text ->
                        dialog.dismiss()
                        when (index) {
                            0 -> {

                            }

                            1 -> lifecycleScope.launch { startScreen<RepositoryScreen>(false) }
                        }
                    }
                }
            }
        }
    }

}