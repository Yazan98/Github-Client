package com.yazan98.domain.actions

import io.vortex.android.VortexAction

interface FollowingAction: VortexAction {
    class GetFollowingUsersAction: FollowingAction
    class GetFollowersUsersAction: FollowingAction
}
