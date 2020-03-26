package com.yazan98.autohub.adapters

import android.view.View
import com.yazan98.autohub.R
import com.yazan98.autohub.adapters.holders.RepositoryTopicsViewHolder
import io.vortex.android.utils.random.VortexBaseAdapter
import javax.inject.Inject

class RepositoryTopicsAdapter @Inject constructor(private val topics: List<String>): VortexBaseAdapter<RepositoryTopicsViewHolder>() {
    override fun getItemCount(): Int {
        return topics.size
    }

    override fun getLayoutRes(): Int {
        return R.layout.row_topic
    }

    override fun getViewHolder(view: View): RepositoryTopicsViewHolder {
        return RepositoryTopicsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepositoryTopicsViewHolder, position: Int) {
        holder.name?.let {
            it.text = topics[position]
        }
    }
}