package com.yazan98.autohub.fragments.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.yazan98.autohub.R
import com.yazan98.autohub.adapters.OrgsAdapter
import com.yazan98.autohub.adapters.RepositoryAdapter
import com.yazan98.autohub.adapters.listeners.RepositoryListener
import com.yazan98.autohub.screen.RepositoryScreen
import com.yazan98.data.ApplicationPrefs
import com.yazan98.data.models.GithubOrg
import com.yazan98.data.models.GithubRepositoryModel
import com.yazan98.data.models.GithubUser
import com.yazan98.domain.actions.ProfileAction
import com.yazan98.domain.models.ProfileViewModel
import com.yazan98.domain.state.ProfileState
import io.vortex.android.ui.VortexErrorType
import io.vortex.android.ui.fragment.VortexFragment
import io.vortex.android.utils.random.VortexImageLoaders
import io.vortex.android.utils.random.VortexRecyclerViewDecoration
import io.vortex.android.utils.ui.goneView
import io.vortex.android.utils.ui.linearHorizontalLayout
import io.vortex.android.utils.ui.linearVerticalLayout
import io.vortex.android.utils.ui.showView
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileFragment @Inject constructor(): VortexFragment<ProfileState, ProfileAction, ProfileViewModel>() {

    private val profileViewModel: ProfileViewModel by viewModels()
    override suspend fun getController(): ProfileViewModel {
        return profileViewModel
    }

    override fun initScreen(view: View) {
        lifecycleScope.launch {
            getController().execute(ProfileAction.GetProfileInfoAction())
            getController().execute(ProfileAction.GetRepositoriesAction())
            getController().execute(ProfileAction.GetOrganizationsAction())
        }
    }

    override suspend fun getLoadingState(newState: Boolean) {
        withContext(Dispatchers.Main) {
            when (newState) {
                true -> {
                    ProfileContainer?.goneView()
                    ProfileProgress?.showView()
                }

                false -> {
                    ProfileContainer?.showView()
                    ProfileProgress?.goneView()
                }
            }
        }
    }

    override suspend fun onStateChanged(newState: ProfileState) {
        withContext(Dispatchers.IO) {
            when (newState) {
                is ProfileState.ErrorResponse -> showError(newState.get(), VortexErrorType.SNACK_BAR)
                is ProfileState.SuccessState -> {
                    newState.get().let {
                        it.profile?.let { showProfileInfo(it) }
                        it.repositories?.let { showRepositories(it) }
                        it.organizations?.let { showOrgs(it) }
                    }
                }
                else -> {

                }
            }
        }
    }

    private suspend fun showOrgs(orgs: List<GithubOrg>) {
        withContext(Dispatchers.Main) {
            when (orgs.isNotEmpty()) {
                true -> {
                    activity?.let {
                        OrgsRecycler?.apply {
                            this.linearHorizontalLayout(it)
                            this.adapter = OrgsAdapter(orgs)
                            (this.adapter as OrgsAdapter).context = it
                        }
                    }
                }

                false -> {
                    OrgsRecycler?.goneView()
                    OrgsText?.goneView()
                }
            }
        }
    }

    private suspend fun showRepositories(response: List<GithubRepositoryModel>) {
        withContext(Dispatchers.Main) {
            activity?.let {
                ProfileRecycler?.apply {
                    this.linearVerticalLayout(it)
                    this.adapter = RepositoryAdapter(response, repoListener)
                    (this.adapter as RepositoryAdapter).context = it
                    this.addItemDecoration(VortexRecyclerViewDecoration(it, LinearLayoutManager.VERTICAL, 5))
                }
            }
        }
    }

    private val repoListener = object: RepositoryListener {
        override fun onRepoClicked(repo: GithubRepositoryModel) {
            lifecycleScope.launch {
                ApplicationPrefs.saveSelectedRepo(repo.name)
                ApplicationPrefs.saveSelectedUsername(repo.owner.login)
                startScreen<RepositoryScreen>(false)
            }
        }
    }

    private suspend fun showProfileInfo(profile: GithubUser) {
        withContext(Dispatchers.Main) {
            ProfileIcon?.apply {
                VortexImageLoaders.loadLargeImageWithFresco(profile.avatar_url, this, 80, 80)
            }

            ProfileName?.let {
                it.text = profile.name
            }

            DescriptionProfile?.let {
                it.text = profile.bio
            }

            ProfileId?.let {
                it.text = profile.login
            }

            FollowProfile?.let {
                it.text = "${profile.followers}: Followers  ${profile.following}: Following"
            }

            ReposCount?.let {
                it.text = "Repositories : ${profile.public_repos + profile.total_private_repos}"
            }

            FollowProfile?.apply {
                this.setOnClickListener {
                    val data = Bundle()
                    data.putString("Name", profile.name)
                    findNavController().navigate(R.id.action_profileFragment_to_profileFollowingFragment, data)
                }
            }
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_profile
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ProfileRecycler?.adapter = null
    }
}