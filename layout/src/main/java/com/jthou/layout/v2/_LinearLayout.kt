package com.jthou.layout.v2

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout


class _LinearLayout(context: Context) : LinearLayout(context), DslViewParent {

    fun <T : View> T.lparams(width: Int = WRAP_LAYOUT, height: Int = WRAP_LAYOUT, init: LayoutParams.() -> Unit) {
        layoutParams = LayoutParams(width, height).also(init)
    }


}