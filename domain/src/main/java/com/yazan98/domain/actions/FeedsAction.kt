package com.yazan98.domain.actions

import io.vortex.android.VortexAction

interface FeedsAction : VortexAction {
    class GetFeedsByUsername : FeedsAction
}