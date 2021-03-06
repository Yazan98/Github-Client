package com.yazan98.autohub.adapters

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import com.yazan98.autohub.R
import com.yazan98.autohub.adapters.holders.RepositoryViewHolder
import com.yazan98.autohub.adapters.listeners.RepositoryListener
import com.yazan98.autohub.utils.LanguageColorUtils
import com.yazan98.data.models.GithubRepositoryModel
import io.vortex.android.utils.random.VortexBaseAdapter
import io.vortex.android.utils.random.VortexImageLoaders
import javax.inject.Inject


class RepositoryAdapter @Inject constructor(
    private val data: List<GithubRepositoryModel>,
    private var listener: RepositoryListener? = null
): VortexBaseAdapter<RepositoryViewHolder>() {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getLayoutRes(): Int {
        return R.layout.row_repository
    }

    override fun getViewHolder(view: View): RepositoryViewHolder {
        return RepositoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.name?.let {
            it.text = data[position].full_name
        }

        holder.fullName?.let {
            it.text = data[position].name
        }

        holder.username?.let {
            it.text = data[position].owner.login
        }

        holder.description?.let {
            it.text = data[position].description
        }

        holder.language?.let {
            it.text = data[position].language
        }

        holder.ownerIcon?.let {
            data[position].owner.avatar_url?.apply {
                VortexImageLoaders.loadLargeImageWithFresco(this, it, 50,50)
            }
        }

        data[position].language?.apply {
            holder.iconLanguage?.let {
                it.setImageBitmap(getCircleImage(this))
//                it.setBackgroundColor(context.getColor(R.color.bg_screens))
            }
        }

        data[position].license?.let { result ->
            holder.license?.let {
                it.text = "License: ${result.name}"
            }
        }

        holder.row?.apply {
            this.setOnClickListener {
                listener?.onRepoClicked(data[position])
            }
        }

    }

    private fun getCircleImage(language: String): Bitmap {
        val bitmap = Bitmap.createBitmap(
            500,  // Width
            300,  // Height
            Bitmap.Config.ARGB_8888 // Config
        )

        val canvas = Canvas(bitmap)

//        canvas.drawColor(context.getColor(R.color.white))

        val paint = Paint()
        paint.style = Paint.Style.FILL
        paint.color = context.getColor(LanguageColorUtils.getColorByLanguage(language))
        paint.isAntiAlias = true

        val radius = Math.min(canvas.width, canvas.height / 2)

        canvas.drawCircle(
            canvas.width / 2.toFloat(),  // cx
            canvas.height / 2.toFloat(),  // cy
            radius - 5f,  // Radius
            paint // Paint
        )

        return bitmap
    }

}
