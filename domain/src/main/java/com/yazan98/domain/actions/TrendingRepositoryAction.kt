package com.yazan98.domain.actions

import io.vortex.android.VortexAction

interface TrendingRepositoryAction : VortexAction {
    class GetTrendingWeeklyRepositories: TrendingRepositoryAction
    class GetTrendingDailyRepositories: TrendingRepositoryAction
    class GetTrendingMonthlyRepositories: TrendingRepositoryAction
}
