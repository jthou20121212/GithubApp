package com.jthou.layout.v1

import android.view.View
import android.view.ViewGroup

interface DslViewParent<out P: ViewGroup.MarginLayoutParams> {

    val <T: View> T.lparams: P
        get() = layoutParams as P

}