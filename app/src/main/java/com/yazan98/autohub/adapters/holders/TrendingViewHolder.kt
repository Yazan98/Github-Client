package com.yazan98.autohub.adapters.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.row_trending_repo.view.*
import javax.inject.Inject

class TrendingViewHolder @Inject constructor(view: View): RecyclerView.ViewHolder(view) {
    val icon: SimpleDraweeView? = view.simpleDraweeView
    val name: TextView? = view.RepoTrendingName
    val description: TextView? = view.TrendingRepoDes
    val stars: TextView? = view.RepoStarts
}