package com.yazan98.autohub.adapters.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.row_repository.view.*
import javax.inject.Inject

class RepositoryViewHolder @Inject constructor(view: View): RecyclerView.ViewHolder(view) {
    val lineColor: View? = view.RepoLineColor
    val ownerIcon: SimpleDraweeView? = view.OwnerIcon
    val name: TextView? = view.RepoName
    val description: TextView? = view.RepoDes
    val iconLanguage: ImageView? = view.RepoLanguageIcon
    val language: TextView? = view.LanguageRepo
    val license: TextView? = view.licenseRepo
}
