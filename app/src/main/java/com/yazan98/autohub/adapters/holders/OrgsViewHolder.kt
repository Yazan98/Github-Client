package com.yazan98.autohub.adapters.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.row_org.view.*
import javax.inject.Inject

class OrgsViewHolder @Inject constructor(view: View): RecyclerView.ViewHolder(view) {
    val icon: SimpleDraweeView? = view.OrgIcon
    val name: TextView? = view.OrgName
}