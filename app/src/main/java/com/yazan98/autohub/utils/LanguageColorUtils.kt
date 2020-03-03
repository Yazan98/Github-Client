package com.yazan98.autohub.utils

import androidx.annotation.ColorRes
import com.yazan98.autohub.R

object LanguageColorUtils {

    @ColorRes
    fun getColorByLanguage(language: String): Int {
        return when (language) {
            "Kotlin" -> R.color.kot_language
            "Vue" -> R.color.vue
            "HTML" -> R.color.web_html
            "JavaScript" -> R.color.js
            "Java" -> R.color.j_lang
            "Groovy" -> R.color.gr
            "Dockerfile" -> R.color.docker
            "Shell" -> R.color.shell
            "C++" -> R.color.cc
            "TypeScript" -> R.color.ts
            "Go" -> R.color.go
            else -> R.color.colorPrimary
        }
    }
}