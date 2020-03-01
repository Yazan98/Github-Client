package com.yazan98.autohub.adapters.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.row_option.view.*
import javax.inject.Inject

class NotificationsViewHolder @Inject constructor(view: View): RecyclerView.ViewHolder(view) {
    val icon: ImageView? = view.OptionIcon
    val name: TextView? = view.OptionName
    val repoName: TextView? = view.RepositoryName
    val readStatus: ImageView? = view.NotificationStatus
    val item: View? = view.NotificationItem
    val ownerImage: SimpleDraweeView? = view.RepoOwnerImageIcon
    val reason: TextView? = view.ReasonNotification
}
