package com.yazan98.autohub.adapters.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.row_feed.view.*
import javax.inject.Inject

class FeedsViewHolder @Inject constructor(view: View): RecyclerView.ViewHolder(view) {
    val image: SimpleDraweeView? = view.FeedImage
    val title: TextView? = view.FeedTitle
    val repoName: TextView? = view.RepoNameFeed
    val action: TextView? = view.FeedAction
}