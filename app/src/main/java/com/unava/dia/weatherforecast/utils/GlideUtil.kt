package com.unava.dia.weatherforecast.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class GlideUtil {
    companion object {
        /**
         * устанавливает изображение во вью
         *
         * @param imageView [ImageView]
         * @param imgUrl [String]
         */
        fun setImage(imageView: ImageView, imgUrl: String) {
            val imageUrl = StringBuilder()
                .append(imgUrl)
                .toString()

            Glide.with(imageView.context).load(imageUrl)
                .into(imageView)
        }
    }
}