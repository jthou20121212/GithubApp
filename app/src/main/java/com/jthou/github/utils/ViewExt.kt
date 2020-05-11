package com.jthou.github.utils

import android.view.ViewManager
import android.widget.TextView
import cn.carbs.android.avatarimageview.library.AvatarImageView
import com.zzhoujay.richtext.RichText

var TextView.markdownText:String
    set(value) {
        RichText.fromMarkdown(value).into(this)
    }
    get() = this.toString()

var TextView.htmlText:String
    set(value) {
        RichText.fromHtml(value).into(this)
    }
    get() = this.toString()

//inline fun ViewManager.avatarImageView(): AvatarImageView = avatarImageView() {}
//inline fun ViewManager.avatarImageView(init: ())