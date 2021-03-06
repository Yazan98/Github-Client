package com.yazan98.autohub.adapters

import android.content.Intent
import android.view.View
import com.yazan98.autohub.R
import com.yazan98.autohub.adapters.holders.OptionViewHolder
import com.yazan98.autohub.screen.StarsScreen
import com.yazan98.autohub.screen.TrendingScreen
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
            it.text = context.getString(options[position].name)
        }

        holder.icon?.let {
            it.setImageResource(options[position].icon)
        }

        holder.item?.apply {
            this.setOnClickListener {
                when (position) {
                    0 -> context.startActivity(Intent(context, StarsScreen::class.java))
                    1 -> context.startActivity(Intent(context, TrendingScreen::class.java))
                }
            }
        }
    }
}