package com.yazan98.autohub.adapters.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.row_option.view.*
import javax.inject.Inject

class OptionsViewHolder @Inject constructor(view: View): RecyclerView.ViewHolder(view) {
    val icon: SimpleDraweeView? = view.OptionIcon
    val name: TextView? = view.OptionName
}
