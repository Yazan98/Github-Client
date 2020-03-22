package com.yazan98.autohub.adapters.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.row_user.view.*

class FollowingViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val icon: SimpleDraweeView? = view.UserIcon
    val username: TextView? = view.UserName
    val description: TextView? = view.UserDescription
}