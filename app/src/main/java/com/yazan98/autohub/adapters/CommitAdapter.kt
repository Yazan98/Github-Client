package com.yazan98.autohub.adapters

import android.view.View
import com.yazan98.autohub.R
import com.yazan98.autohub.adapters.holders.CommitsViewHolder
import com.yazan98.data.models.GithubCommit
import io.vortex.android.utils.random.VortexBaseAdapter
import io.vortex.android.utils.random.VortexImageLoaders
import javax.inject.Inject

class CommitAdapter @Inject constructor(private val list: List<GithubCommit>) :
    VortexBaseAdapter<CommitsViewHolder>() {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getLayoutRes(): Int {
        return R.layout.row_commit
    }

    override fun getViewHolder(view: View): CommitsViewHolder {
        return CommitsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommitsViewHolder, position: Int) {
        holder.commiterName?.let {
            it.text = list[position].commit.committer.name
        }

        holder.icon?.let {
            if (list[position].author.avatar_url != null) {
                list[position].author.avatar_url?.let { it1 ->
                    VortexImageLoaders.loadLargeImageWithFresco(
                        it1, it, 400, 400
                    )
                }
            }
        }

        holder.email?.let {
            it.text = list[position].commit.committer.email
        }

        holder.message?.let {
            it.text = list[position].commit.message
        }
    }
}