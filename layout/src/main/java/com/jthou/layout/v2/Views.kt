package com.jthou.layout.v2

import android.app.Activity
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout

inline fun <reified T : ViewGroup> T.linearLayout(init: (@DslViewMaker _LinearLayout).() -> Unit) =
    _LinearLayout(context).also(this::addView).also(init)

inline fun <reified T : ViewGroup> T.verticalLayout(init: (@DslViewMaker _LinearLayout).() -> Unit) =
    _LinearLayout(context).also(this::addView).apply {
        orientation = LinearLayout.VERTICAL
        init()
    }

inline fun <reified T : Activity> T.linearLayout(init: (@DslViewMaker _LinearLayout).() -> Unit) =
    _LinearLayout(this).also(this::setContentView).also(init)

inline fun <reified T : Activity> T.verticalLayout(init: (@DslViewMaker _LinearLayout).() -> Unit) =
    _LinearLayout(this).also(this::setContentView).apply {
        orientation = LinearLayout.VERTICAL
        init()
    }

inline fun <reified T : ViewGroup> T.frameLayout(init: (@DslViewMaker _FrameLayout).() -> Unit) =
    _FrameLayout(context).also(this::addView).also(init)

inline fun <reified T : Activity> T.frameLayout(init: (@DslViewMaker _FrameLayout).() -> Unit) =
    _FrameLayout(this).also(this::setContentView).also(init)

inline fun <reified T : ViewGroup> T.button(init: (@DslViewMaker Button).() -> Unit) =
    Button(context).also(this::addView).also(init)

inline fun <reified T : Activity> T.button(init: (@DslViewMaker Button).() -> Unit) =
    Button(this).also(this::setContentView).also(init)
