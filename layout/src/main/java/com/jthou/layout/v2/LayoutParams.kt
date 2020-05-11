package com.jthou.layout.v2

import android.widget.RelativeLayout
import androidx.annotation.IdRes

fun <T: RelativeLayout.LayoutParams> T.leftOf(@IdRes id: Int) {
    addRule(RelativeLayout.LEFT_OF, id)
}

fun <T: RelativeLayout.LayoutParams> T.centerInParent(@IdRes id: Int) {
    addRule(RelativeLayout.CENTER_IN_PARENT, id)
}