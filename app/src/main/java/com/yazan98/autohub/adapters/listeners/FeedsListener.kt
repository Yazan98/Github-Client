package com.yazan98.autohub.adapters.listeners

import com.yazan98.data.models.FeedResponse

interface FeedsListener {
    fun onFeedClicked(item: FeedResponse)
}