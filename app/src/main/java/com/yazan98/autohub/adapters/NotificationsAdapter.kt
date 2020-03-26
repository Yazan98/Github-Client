package com.yazan98.autohub.adapters

import android.view.View
import com.yazan98.autohub.R
import com.yazan98.autohub.adapters.holders.NotificationsViewHolder
import com.yazan98.autohub.adapters.listeners.NotificationListener
import com.yazan98.data.models.GithubNotification
import io.vortex.android.utils.random.VortexBaseAdapter
import io.vortex.android.utils.random.VortexImageLoaders
import timber.log.Timber
import javax.inject.Inject

class NotificationsAdapter @Inject constructor(
    private val options: List<GithubNotification>,
    private var listener: NotificationListener? = null
) : VortexBaseAdapter<NotificationsViewHolder>() {

    override fun getLayoutRes(): Int {
        return R.layout.row_option
    }

    override fun getViewHolder(view: View): NotificationsViewHolder {
        return NotificationsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return options.size
    }

    override fun onBindViewHolder(holder: NotificationsViewHolder, position: Int) {
        holder.name?.apply {
            this.text = options[position].subject.title
        }

        holder.icon?.let {
            if (options[position].subject.type.equals("PullRequest")) {
                it.setImageResource(R.drawable.pull_request)
            } else if(options[position].subject.type.equals("Issue")) {
                it.setImageResource(R.drawable.ic_warning)
            }
        }

        holder.repoName?.let {
            it.text = options[position].repository.full_name
        }

        holder.readStatus?.let {
            if (options[position].unread) {
                it.setImageResource(R.drawable.bg_read)
            } else {
                it.setImageResource(R.drawable.bg_unread)
            }
        }

        holder.item?.apply {
            this.setOnClickListener {
                listener?.onNotificationSelected(options[position].repository)
            }
        }

        holder.ownerImage?.let {
            options[position].repository.owner.avatar_url?.apply {
                VortexImageLoaders.loadLargeImageWithFresco(this, it, 30, 30)
            }
        }

        holder.reason?.let {
            it.text = options[position].reason
        }

    }
}