package com.jthou.github.utils

import cn.carbs.android.avatarimageview.library.AppCompatAvatarImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun AppCompatAvatarImageView.loadWithGlide(url: String, textPlaceHolder: Char, requestOptions: RequestOptions = RequestOptions()) {
    textPlaceHolder.toString().let {
        setTextAndColorSeed(it.toUpperCase(), it.hashCode().toString())
    }
    Glide
        .with(context)
        .load(url)
        .apply(requestOptions)
        .into(this)
}