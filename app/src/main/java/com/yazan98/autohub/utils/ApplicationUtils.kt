package com.yazan98.autohub.utils

import com.yazan98.autohub.R
import com.yazan98.data.models.internal.Option

object ApplicationUtils {

    fun getMainScreenOptions(): List<Option> {
        return arrayListOf(
           Option(R.string.starred_repos, R.drawable.star),
           Option(R.string.trending, R.drawable.trending)
        )
    }
}