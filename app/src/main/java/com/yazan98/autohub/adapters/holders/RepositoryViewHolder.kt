package com.yazan98.autohub.adapters.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.row_repository.view.*
import javax.inject.Inject

class RepositoryViewHolder @Inject constructor(view: View): RecyclerView.ViewHolder(view) {
    val ownerIcon: SimpleDraweeView? = view.RepoImage
    val fullName: TextView? = view.RepoFullName
    val name: TextView? = view.RepoName
    val description: TextView? = view.RepoDescription
    val iconLanguage: ImageView? = view.LanguageIcon
    val language: TextView? = view.RepoLanguage
    val license: TextView? = view.RepoLicesnse
    val username: TextView? = view.RepoOwner
    val row: View? = view.RepoItem
}
