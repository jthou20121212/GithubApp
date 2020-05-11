package com.jthou.github.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import splitties.dimensions.dp
import splitties.views.dsl.core.Ui
import splitties.views.dsl.core.textView
import splitties.views.textColorResource

class MyIssueFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return MyIssueUi(container?.context ?: activity!!).root
    }

}

class MyIssueUi(override val ctx: Context) : Ui {

    override val root: View = textView {
        text = "my issue"
        textSize = dp(20)
        textColorResource = android.R.color.white
        setBackgroundResource(android.R.color.black)
    }.also {
        it.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

}
