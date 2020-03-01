package com.yazan98.autohub.utils

import com.yazan98.autohub.R
import com.yazan98.data.models.OptionModel

object ApplicationUtils {

    fun getMainScreenOptions(): List<OptionModel> {
        return arrayListOf(
            OptionModel("Profile", R.drawable.ic_user),
            OptionModel("Issues", R.drawable.ic_issue)
        )
    }
}