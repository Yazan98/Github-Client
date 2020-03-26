package com.yazan98.autohub.adapters.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.row_contributer.view.*
import javax.inject.Inject

class ContributersViewHolder @Inject constructor(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView? = view.ContributerName
    val icon: SimpleDraweeView? = view.simpleDraweeView2
    val login: TextView? = view.LoginContributer
}