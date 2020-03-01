package com.yazan98.autohub.adapters.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_repository.view.*
import javax.inject.Inject

class RepositoryViewHolder @Inject constructor(view: View): RecyclerView.ViewHolder(view) {
    val name: TextView? = view.RepositoryNameStarred
    val langauge: TextView? = view.StarredRepositoryLanguage
    val description: TextView? = view.RepositoryStarredDescription
}