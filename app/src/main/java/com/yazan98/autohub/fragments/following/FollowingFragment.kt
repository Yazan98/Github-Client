package com.yazan98.autohub.fragments.following

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.yazan98.autohub.R
import com.yazan98.autohub.adapters.FollowingAdapter
import com.yazan98.domain.actions.FollowingAction
import com.yazan98.domain.models.FollowingViewModel
import com.yazan98.domain.state.FollowingState
import io.vortex.android.ui.fragment.VortexFragment
import io.vortex.android.utils.random.VortexRecyclerViewDecoration
import io.vortex.android.utils.ui.linearVerticalLayout
import kotlinx.android.synthetic.main.fragment_following.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FollowingFragment @Inject constructor():
    VortexFragment<FollowingState, FollowingAction, FollowingViewModel>() {

    private val viewModel: FollowingViewModel by viewModels()

    override suspend fun getController(): FollowingViewModel {
        return viewModel
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_following
    }

    override suspend fun getLoadingState(newState: Boolean) {

    }

    override fun initScreen(view: View) {

        viewModel.followingObserver.observe(this, Observer {
            lifecycleScope.launch {
                onStateChanged(it)
            }
        })

        lifecycleScope.launch {
            getController().execute(FollowingAction.GetFollowingUsersAction())
        }

    }

    override suspend fun onStateChanged(newState: FollowingState) {
        withContext(Dispatchers.Main) {
            if (newState is FollowingState.FollowingUsersState) {
                activity?.let {
                    FollowingRecycler?.apply {
                        this.linearVerticalLayout(it)
                        this.adapter = FollowingAdapter(newState.get())
                        (this.adapter as FollowingAdapter).context = it
                        this.addItemDecoration(VortexRecyclerViewDecoration(it, LinearLayoutManager.VERTICAL, 5))
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        FollowingRecycler?.adapter = null
    }
}