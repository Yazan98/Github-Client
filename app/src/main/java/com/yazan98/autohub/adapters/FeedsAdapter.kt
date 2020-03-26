package com.yazan98.autohub.adapters

import android.view.View
import com.yazan98.autohub.R
import com.yazan98.autohub.adapters.holders.FeedsViewHolder
import com.yazan98.autohub.adapters.listeners.FeedsListener
import com.yazan98.data.models.FeedResponse
import io.vortex.android.utils.random.VortexBaseAdapter
import io.vortex.android.utils.random.VortexImageLoaders
import javax.inject.Inject

class FeedsAdapter @Inject constructor(
    private val response: List<FeedResponse>,
    private var listener: FeedsListener? = null
): VortexBaseAdapter<FeedsViewHolder>() {

    override fun getItemCount(): Int {
        return response.size
    }

    override fun getLayoutRes(): Int {
        return R.layout.row_feed
    }

    override fun getViewHolder(view: View): FeedsViewHolder {
       return FeedsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedsViewHolder, position: Int) {
        holder.action?.let {
            it.text = "${context.getString(R.string.action)} ${response[position].payload.action} - ${context.getString(R.string.type)} ${response[position].type}"
        }

        holder.image?.let {
            response[position].actor.avatar_url?.apply {
                VortexImageLoaders.loadLargeImageWithFresco(this, it, 700, 700)
            }
        }

        holder.title?.let {
            it.text = response[position].actor.login
        }

        holder.repoName?.let {
            it.text = "${context.getString(R.string.repo_name)} ${response[position].repo.name}"
        }

        holder.item?.setOnClickListener {
            listener?.onFeedClicked(response[position])
        }
    }
}