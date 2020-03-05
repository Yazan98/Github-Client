package com.yazan98.autohub.fragments.auth

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputLayout
import com.yazan98.autohub.R
import com.yazan98.autohub.utils.InputType
import com.yazan98.data.models.LoginInfo
import com.yazan98.domain.actions.ProfileAction
import com.yazan98.domain.models.ProfileViewModel
import com.yazan98.domain.state.ProfileState
import io.vortex.android.ui.fragment.VortexBaseFragment
import io.vortex.android.ui.fragment.VortexFragment
import io.vortex.android.utils.ui.goneView
import io.vortex.android.utils.ui.showView
import kotlinx.android.synthetic.main.fragment_auth.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthFragment : VortexFragment<ProfileState, ProfileAction, ProfileViewModel>() {

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun initScreen(view: View) {
        LoginAccountButton?.apply {
            this.setOnClickListener {
                when {
                    UsernameField?.text.toString().trim().isEmpty() -> showError(UsernameContainer, InputType.USERNAME)
                    PasswordField?.text.toString().trim().isEmpty() -> showError(PasswordContainer, InputType.PASS)
                    else -> lifecycleScope.launch {
                        getController().execute(ProfileAction.LoginAccountInfoAction(
                            LoginInfo(
                                UsernameField?.text.toString().trim(),
                                PasswordField?.text.toString().trim()
                            )
                        ))
                    }
                }
            }
        }
    }

    override suspend fun getController(): ProfileViewModel {
        return profileViewModel
    }

    private fun showError(container: TextInputLayout?, type: InputType) {
        container?.let {
            when (type) {
                InputType.USERNAME -> it.error = getString(R.string.username_required)
                InputType.PASS -> it.error = getString(R.string.pass_required)
            }
        }
    }

    override suspend fun getLoadingState(newState: Boolean) {
        withContext(Dispatchers.Main) {
            when (newState) {
                true -> {
                    LoginContainer?.goneView()
                    LoginLoader?.showView()
                }

                false -> {
                    LoginContainer?.showView()
                    LoginLoader?.goneView()
                }
            }
        }
    }

    override suspend fun onStateChanged(newState: ProfileState) {
        withContext(Dispatchers.IO) {
            when (newState) {

            }
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_auth
    }

}
