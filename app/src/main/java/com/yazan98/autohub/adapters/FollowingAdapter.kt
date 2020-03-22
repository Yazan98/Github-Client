package com.yazan98.autohub.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yazan98.autohub.R
import com.yazan98.autohub.adapters.holders.FollowingViewHolder
import com.yazan98.data.database.models.GithubAccount
import com.yazan98.data.models.GithubUser
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import io.vortex.android.utils.random.VortexBaseAdapter
import io.vortex.android.utils.random.VortexImageLoaders
import javax.inject.Inject

class FollowingAdapter @Inject constructor(private val result: OrderedRealmCollection<GithubAccount>):
    RealmRecyclerViewAdapter<GithubAccount, FollowingViewHolder>(result, true) {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.row_user, parent, false)
        return FollowingViewHolder(v)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        result[position].login.apply {
            holder.username?.let {
                it.text = this
            }
        }

        result[position].bio.apply {
            holder.description?.let {
                it.text = this
            }
        }

        result[position].avatar_url.apply {
            holder.icon?.let {
                VortexImageLoaders.loadLargeImageWithFresco(this, it, 500, 500)
            }
        }

    }

}

//class FollowingAdapter @Inject constructor(private val result: List<GithubUser>): VortexBaseAdapter<FollowingViewHolder>() {
//
//    override fun getItemCount(): Int {
//        return result.size
//    }
//
//    override fun getLayoutRes(): Int {
//        return R.layout.row_user
//    }
//
//    override fun getViewHolder(view: View): FollowingViewHolder {
//        return FollowingViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
//        result[position].login.apply {
//            holder.username?.let {
//                it.text = this
//            }
//        }
//
//        result[position].bio.apply {
//            holder.description?.let {
//                it.text = this
//            }
//        }
//
//        result[position].avatar_url.apply {
//            holder.icon?.let {
//                VortexImageLoaders.loadLargeImageWithFresco(this, it, 500, 500)
//            }
//        }
//
//    }
//
//}
//
