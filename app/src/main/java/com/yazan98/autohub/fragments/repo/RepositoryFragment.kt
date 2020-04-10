package com.yazan98.autohub.fragments.repo

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.yazan98.autohub.R
import com.yazan98.autohub.adapters.RepositoryFragmentsAdapter
import com.yazan98.data.models.GithubRepositoryModel
import com.yazan98.data.models.internal.RepoInfo
import com.yazan98.domain.actions.RepositoryAction
import com.yazan98.domain.models.RepositoryViewModel
import com.yazan98.domain.state.RepositoryState
import io.vortex.android.ui.VortexErrorType
import io.vortex.android.ui.fragment.VortexBaseFragment
import io.vortex.android.ui.fragment.VortexFragment
import kotlinx.android.synthetic.main.fragment_repository.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class RepositoryFragment : VortexBaseFragment() {

    override fun initScreen(view: View) {
        RepositoryViewPager?.apply {
            activity?.let {
                this.adapter = RepositoryFragmentsAdapter(it.supportFragmentManager)
            }
        }

        bottomNavigationView?.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.RepoDetails -> {
                    RepositoryViewPager?.currentItem = 0
                    true
                }

                R.id.BranchesButton -> {
                    RepositoryViewPager?.currentItem = 1
                    true
                }

                R.id.CommitsButton -> {
                    RepositoryViewPager?.currentItem = 2
                    true
                }
                else -> true
            }
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_repository
    }

}
