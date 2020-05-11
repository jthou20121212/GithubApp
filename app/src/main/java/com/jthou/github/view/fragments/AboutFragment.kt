package com.jthou.github.view.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jthou.github.R
import com.jthou.github.utils.markdownText
import com.jthou.github.view.common.CommonSinglePageFragment
import splitties.alertdialog.alertDialog
import splitties.dimensions.dip
import splitties.views.dsl.core.*
import splitties.views.imageResource
import splitties.views.onClick
import splitties.views.padding
import splitties.views.textColorResource

class AboutFragment : CommonSinglePageFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return object : Ui {
            override val ctx = container?.context ?: activity!!
            override val root = verticalLayout {

                imageView {
                    imageResource = R.mipmap.ic_launcher
                }.let(::addView)

                val authorTv = textView() {
                    text = ctx.getText(R.string.author)
                }
                authorTv.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                    .apply {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }
                authorTv.let(::addView)

                val tv = textView() {
                    text = ctx.getText(R.string.open_source_licenses)
                    onClick {
                        ctx.alertDialog {

                        }.apply {
                            setView(
                                textView {
                                    padding = dip(10)
                                    markdownText =
                                        ctx.assets.open("licenses.md").bufferedReader().readText()
                                }.wrapInScrollView { }
                            )
                        }.show()
                    }
                }
                tv.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                    .apply {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }
                tv.let(::addView)
            }.apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                    .apply {
                        gravity = Gravity.CENTER
                    }
            }
        }.root
    }


}