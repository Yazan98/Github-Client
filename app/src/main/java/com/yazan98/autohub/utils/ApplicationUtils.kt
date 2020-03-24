package com.yazan98.autohub.utils

import com.yazan98.data.models.internal.Option

object ApplicationUtils {

    fun getMainScreenOptions(): List<Option> {
        return arrayListOf(
            Option(1, "Search"),
            Option(2, "Gists"),
            Option(3, "Stared Repositories"),
            Option(3, "About App"),
            Option(3, "Open Source Libraries")
        )
    }
}