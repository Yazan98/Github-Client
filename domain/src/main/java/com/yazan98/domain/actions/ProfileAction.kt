package com.yazan98.domain.actions

import com.yazan98.data.models.internal.LoginInfo
import io.vortex.android.VortexAction

interface ProfileAction: VortexAction {
    class GetProfileInfoAction: ProfileAction
    class GetOrganizationsAction: ProfileAction
    class GetRepositoriesAction: ProfileAction
    class LoginAccountInfoAction(private val info: LoginInfo): ProfileAction {
        fun get() = info
    }
}
