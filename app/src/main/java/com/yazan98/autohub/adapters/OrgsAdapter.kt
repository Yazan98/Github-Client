package com.yazan98.autohub.adapters

import android.view.View
import com.yazan98.autohub.R
import com.yazan98.autohub.adapters.holders.OrgsViewHolder
import com.yazan98.data.models.GithubOrg
import io.vortex.android.utils.random.VortexBaseAdapter
import io.vortex.android.utils.random.VortexImageLoaders
import javax.inject.Inject

class OrgsAdapter @Inject constructor(private val orgs: List<GithubOrg>): VortexBaseAdapter<OrgsViewHolder>() {

    override fun getItemCount(): Int {
        return orgs.size
    }

    override fun getLayoutRes(): Int {
        return R.layout.row_org
    }

    override fun getViewHolder(view: View): OrgsViewHolder {
        return OrgsViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrgsViewHolder, position: Int) {
        holder.icon?.let {
            VortexImageLoaders.loadLargeImageWithFresco(orgs[position].avatar_url, it, 700, 700)
        }

        holder.name?.let {
            it.text = orgs[position].login
        }
    }

}
