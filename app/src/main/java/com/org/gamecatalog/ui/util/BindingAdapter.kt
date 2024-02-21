package com.org.gamecatalog.ui.util

import android.graphics.drawable.Drawable
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl", "error")
fun loadImage(image: AppCompatImageView, imgUrl: String?, error: Drawable) {
  Glide.with(image.context)
    .load(imgUrl)
    .error(error)
    .into(image)
}

@BindingAdapter("isVisible")
fun hide(v: View, isVisible: Boolean) {
  v.visibility = if(isVisible) View.VISIBLE else View.GONE
}