package com.jthou.layout.v1

import android.content.Context
import android.view.View
import android.widget.LinearLayout


class _LinearLayout(context: Context) : LinearLayout(context),
    DslViewParent<LinearLayout.LayoutParams> {

    var <T : View> T.layoutWeight: Float
        set(value) {
            lparams.weight = value
        }
        get() {
            return lparams.weight
        }

    var <T : View> T.layoutGravity: Int
        set(value) {
            lparams.gravity = value
        }
        get() {
            return lparams.gravity
        }

    var <T : View> T.layoutWidth: Int
        set(value) {
            lparams.width = value
        }
        get() {
            return lparams.width
        }

    var <T : View> T.layoutHeight: Int
        set(value) {
            lparams.height = value
        }
        get() {
            return lparams.height
        }

}