package com.yazan98.autohub.adapters.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_option_setting.view.*
import javax.inject.Inject

class OptionViewHolder @Inject constructor(view: View): RecyclerView.ViewHolder(view) {
    val name: TextView? = view.OptionItem
}