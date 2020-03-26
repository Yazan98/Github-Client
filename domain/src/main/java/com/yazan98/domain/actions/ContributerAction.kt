package com.yazan98.domain.actions

import io.vortex.android.VortexAction

interface ContributerAction: VortexAction {
    class GetContributersByRepoInfo: ContributerAction
}
