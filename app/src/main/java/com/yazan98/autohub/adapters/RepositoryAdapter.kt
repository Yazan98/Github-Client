package com.yazan98.autohub.adapters

import android.view.View
import com.yazan98.autohub.R
import com.yazan98.autohub.adapters.holders.RepositoryViewHolder
import com.yazan98.data.models.GithubRepositoryModel
import io.vortex.android.utils.random.VortexBaseAdapter
import javax.inject.Inject

class RepositoryAdapter @Inject constructor(private val data: List<GithubRepositoryModel>): VortexBaseAdapter<RepositoryViewHolder>() {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getLayoutRes(): Int {
        return R.layout.row_repository
    }

    override fun getViewHolder(view: View): RepositoryViewHolder {
        return RepositoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.name?.let {
            it.text = data[position].name
        }

        holder.description?.let {
            it.text = data[position].description
        }

        holder.langauge?.let {
            it.text = data[position].language
        }
    }

}
