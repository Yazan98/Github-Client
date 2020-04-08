package com.yazan98.autohub.fragments.main

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.yazan98.autohub.AutohubApplication
import com.yazan98.autohub.R
import com.yazan98.autohub.adapters.NotificationsAdapter
import com.yazan98.autohub.adapters.listeners.NotificationListener
import com.yazan98.autohub.dialogs.RepositoryDialog
import com.yazan98.data.models.GithubNotification
import com.yazan98.data.models.GithubRepositoryModel
import com.yazan98.domain.actions.HomeAction
import com.yazan98.domain.models.NotificationsViewModel
import com.yazan98.domain.state.HomeState
import io.vortex.android.ui.VortexErrorType
import io.vortex.android.ui.fragment.VortexFragment
import io.vortex.android.utils.random.VortexRecyclerViewDecoration
import io.vortex.android.utils.ui.goneView
import io.vortex.android.utils.ui.linearVerticalLayout
import io.vortex.android.utils.ui.showView
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotificationsFragment : VortexFragment<HomeState, HomeAction, NotificationsViewModel>() {

    private val viewModel: NotificationsViewModel by viewModels()
    private val repositoryDialog: RepositoryDialog by lazy {
        RepositoryDialog()
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_main
    }

    override fun getController(): NotificationsViewModel {
        return viewModel
    }

    override fun initScreen(view: View) {
        (activity?.application as AutohubApplication).let {
            lifecycleScope.launch {
                getController().execute(HomeAction.GetNotificationsAction())
//                it.startGithubActions()
            }
        }
    }

    override suspend fun getLoadingState(newState: Boolean) {
        withContext(Dispatchers.Main) {
            when (newState) {
                true -> {
                    NotificationsProgress?.showView()
                    MainRecycler?.goneView()
                }

                false -> {
                    NotificationsProgress?.goneView()
                    MainRecycler?.showView()
                }
            }
        }
    }

    override suspend fun onStateChanged(newState: HomeState) {
        withContext(Dispatchers.IO) {
            when (newState) {
                is HomeState.ErrorState -> showError(newState.get(), VortexErrorType.SNACK_BAR)
                is HomeState.NotificationsState -> showNotificationsRecycler(newState.get())
            }
        }
    }

    private suspend fun showNotificationsRecycler(response: List<GithubNotification>) {
        withContext(Dispatchers.Main) {
            activity?.let {
                MainRecycler?.apply {
                    this.linearVerticalLayout(it)
                    this.adapter = NotificationsAdapter(response, listener)
                    (this.adapter as NotificationsAdapter).context = it
                    this.addItemDecoration(
                        VortexRecyclerViewDecoration(it, LinearLayoutManager.VERTICAL, 5)
                    )
                }
            }
        }
    }

    private val listener = object: NotificationListener {
        override fun onNotificationSelected(repo: GithubRepositoryModel) {
            activity?.let {
                repositoryDialog.showRepositoryInfo(repo, it.supportFragmentManager)
                it.supportFragmentManager.executePendingTransactions()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        MainRecycler?.adapter = null
    }

}
