package com.yazan98.autohub.adapters

import android.view.View
import com.yazan98.autohub.R
import com.yazan98.autohub.adapters.holders.ContributersViewHolder
import com.yazan98.data.models.GithubUser
import io.vortex.android.utils.random.VortexBaseAdapter
import io.vortex.android.utils.random.VortexImageLoaders
import javax.inject.Inject

class ContributersAdapter @Inject constructor(private val contributers: List<GithubUser>): VortexBaseAdapter<ContributersViewHolder>() {

    override fun getItemCount(): Int {
        return contributers.size
    }

    override fun getLayoutRes(): Int {
        return R.layout.row_contributer
    }

    override fun getViewHolder(view: View): ContributersViewHolder {
        return ContributersViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContributersViewHolder, position: Int) {
        holder.icon?.let {
            contributers[position].avatar_url?.apply {
                VortexImageLoaders.loadLargeImageWithFresco(this, it, 400, 400)
            }
        }

        holder.login?.let {
            it.text = contributers[position].login
        }

        holder.name?.let {
            it.text = "${contributers[position].contributions} ${context.getString(R.string.contributions)}"
        }
    }

}
