package com.yazan98.domain.actions

import io.vortex.android.VortexAction

interface HomeAction : VortexAction {
    class GetFeedsAction: HomeAction
    class GetNotificationsAction: HomeAction
}