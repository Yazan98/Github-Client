package com.yazan98.data.models

data class TrendingRepo(
    var author: String = "",
    var name: String = "",
    var avatar: String = "",
    var url: String = "",
    var description: String = "",
    var language: String = "",
    var stars: Long = 0,
    var forks: Long = 0,
    var builtBy: List<TrendingAccount>
)

data class TrendingAccount(
    val username: String,
    val avatar: String
)