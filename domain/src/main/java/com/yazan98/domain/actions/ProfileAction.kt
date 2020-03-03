package com.yazan98.domain.actions

import io.vortex.android.VortexAction

interface ProfileAction: VortexAction {
    class GetProfileInfoAction: ProfileAction
    class GetRepositoriesAction: ProfileAction
}
