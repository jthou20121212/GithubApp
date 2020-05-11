package com.jthou.github.view.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.jthou.github.R
import com.jthou.layout.v2.centerInParent
import splitties.dimensions.dip
import splitties.views.backgroundColor
import splitties.views.dsl.core.matchParent
import splitties.views.dsl.core.wrapContent
import splitties.views.padding

/**
 * Created by benny on 7/15/17.
 */
class ErrorInfoView(val parentView: ViewGroup) : RelativeLayout(parentView.context) {

    private var textView: TextView

    var isShowing = false

    init {
        backgroundColor = ContextCompat.getColor(context, R.color.colorBackground)
        textView = TextView(context)
        textView.apply {
            textSize = 18f
            setTextColor(Color.BLACK)
            padding = dip(5)
            gravity = Gravity.CENTER
            layoutParams = LayoutParams(wrapContent, wrapContent).apply { addRule(CENTER_IN_PARENT) }
            this@ErrorInfoView.addView(this)
        }
    }

    fun show(text: String) {
        if (!isShowing) {
            parentView.addView(this, matchParent, matchParent)
            alpha = 0f
            animate().alpha(1f).setDuration(100).start()
            isShowing = true
        }
        textView.text = text
    }

    fun dismiss() {
        if (isShowing) {
            parentView.startViewTransition(this)
            parentView.removeView(this)
            animate().alpha(0f).setDuration(100).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    parentView.endViewTransition(this@ErrorInfoView)
                }
            }).start()
            isShowing = false
        }
    }

    fun show(@StringRes textRes: Int) {
        show(context.getString(textRes))
    }
}