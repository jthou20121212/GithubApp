package com.jthou.github.view.ui

import android.content.Context
import android.view.View
import android.view.ViewGroup.LayoutParams
import splitties.dimensions.dp
import splitties.views.dsl.core.Ui
import splitties.views.dsl.core.textView
import splitties.views.textColorResource

class TestUi(override val ctx: Context) : Ui {

    override val root: View = textView {
        text = "text ui"
        textSize = dp(20)
        textColorResource = android.R.color.white
        setBackgroundResource(android.R.color.black)
    }.also {
        it.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }

}