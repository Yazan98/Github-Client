package com.yazan98.autohub.adapters.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.row_commit.view.*
import javax.inject.Inject

class CommitsViewHolder @Inject constructor(view: View): RecyclerView.ViewHolder(view) {
    val message: TextView? = view.CommitMessage
    val commiterName: TextView? = view.CommiterName
    val icon: SimpleDraweeView? = view.CommiterIcon
    val email: TextView? = view.CommiterEmail
}