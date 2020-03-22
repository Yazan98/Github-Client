package com.yazan98.autohub.fragments

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.yazan98.autohub.R
import com.yazan98.autohub.adapters.RepositoryAdapter
import com.yazan98.data.models.GithubRepositoryModel
import com.yazan98.data.models.GithubUser
import com.yazan98.domain.actions.ProfileAction
import com.yazan98.domain.models.ProfileViewModel
import com.yazan98.domain.state.ProfileState
import io.vortex.android.ui.VortexErrorType
import io.vortex.android.ui.fragment.VortexFragment
import io.vortex.android.utils.random.VortexImageLoaders
import io.vortex.android.utils.ui.goneView
import io.vortex.android.utils.ui.linearVerticalLayout
import io.vortex.android.utils.ui.showView
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_starred_repos.*
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
                    }
                }
                else -> {

                }
            }
        }
    }

    private suspend fun showRepositories(response: List<GithubRepositoryModel>) {
        withContext(Dispatchers.Main) {
            activity?.let {
                ProfileRecycler?.apply {
                    this.linearVerticalLayout(it)
                    this.adapter = RepositoryAdapter(response)
                    (this.adapter as RepositoryAdapter).context = it
                }
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
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_profile
    }
}