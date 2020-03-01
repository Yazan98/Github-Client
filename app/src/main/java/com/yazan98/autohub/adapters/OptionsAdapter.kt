package com.yazan98.autohub.adapters

import android.net.Uri
import android.view.View
import com.yazan98.autohub.R
import com.yazan98.autohub.adapters.holders.OptionsViewHolder
import com.yazan98.data.models.OptionModel
import io.vortex.android.utils.random.VortexBaseAdapter
import io.vortex.android.utils.random.VortexImageLoaders
import javax.inject.Inject

class OptionsAdapter @Inject constructor(private val options: List<OptionModel>) : VortexBaseAdapter<OptionsViewHolder>() {

    override fun getLayoutRes(): Int {
        return R.layout.row_option
    }

    override fun getViewHolder(view: View): OptionsViewHolder {
        return OptionsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return options.size
    }

    override fun onBindViewHolder(holder: OptionsViewHolder, position: Int) {
        holder.icon?.setActualImageResource(R.drawable.ic_user)

        holder.name?.apply {
            this.text = options[position].name
        }
    }
}