package com.yazan98.autohub.adapters

import android.view.View
import com.yazan98.autohub.R
import com.yazan98.autohub.adapters.holders.TrendingViewHolder
import com.yazan98.autohub.adapters.listeners.RepositoryListener
import com.yazan98.data.models.TrendingRepo
import io.vortex.android.utils.random.VortexBaseAdapter
import io.vortex.android.utils.random.VortexImageLoaders
import javax.inject.Inject

class TrendingAdapter @Inject constructor(
    private val result: List<TrendingRepo>,
    private val listener: TrendingRepoListener? = null
): VortexBaseAdapter<TrendingViewHolder>() {

    override fun getItemCount(): Int {
        return result.size
    }

    override fun getLayoutRes(): Int {
        return R.layout.row_trending_repo
    }

    override fun getViewHolder(view: View): TrendingViewHolder {
        return TrendingViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        holder.icon?.let {
            VortexImageLoaders.loadLargeImageWithFresco(result[position].avatar, it, 700, 700)
        }

        holder.description?.let {
            it.text = result[position].description
        }

        holder.name?.let {
            it.text = "${result[position].author}/${result[position].name}"
        }

        holder.stars?.let {
            it.text = "Stars : ${result[position].stars} Forks : ${result[position].forks}"
        }

        holder.item?.let {
            it.setOnClickListener {
                listener?.onRepoClicked(result[position])
            }
        }
    }
}

interface TrendingRepoListener {
    fun onRepoClicked(repo: TrendingRepo)
}