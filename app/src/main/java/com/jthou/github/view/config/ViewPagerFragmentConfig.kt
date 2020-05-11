package com.jthou.github.view.config

import androidx.fragment.app.Fragment

interface ViewPagerFragmentConfig {

    fun getFragmentPagesLoggedIn() : List<FragmentPage>

    fun getFragmentPagesNotLoggedIn() : List<FragmentPage>

}

data class FragmentPage(val fragment: Fragment, val title: String)