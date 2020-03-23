package com.yazan98.autohub.adapters

import android.view.View
import com.yazan98.autohub.R
import com.yazan98.autohub.adapters.holders.OptionViewHolder
import com.yazan98.autohub.utils.ApplicationUtils
import com.yazan98.data.models.internal.Option
import io.vortex.android.utils.random.VortexBaseAdapter

class SettingsAdapter constructor(
    private val options: List<Option> = ApplicationUtils.getMainScreenOptions()
) : VortexBaseAdapter<OptionViewHolder>() {

    override fun getItemCount(): Int {
        return options.size
    }

    override fun getLayoutRes(): Int {
        return R.layout.row_option_setting
    }

    override fun getViewHolder(view: View): OptionViewHolder {
        return OptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.name?.let {
            it.text = options[position].name
        }
    }
}