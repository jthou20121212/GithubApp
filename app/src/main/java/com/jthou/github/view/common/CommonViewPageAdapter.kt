package com.jthou.github.view.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import com.jthou.github.utils.ViewPagerAdapterList
import com.jthou.github.view.config.FragmentPage

class CommonViewPageAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    val fragmentPages = ViewPagerAdapterList<FragmentPage>(this)

    override fun getItem(position: Int): Fragment {
        return fragmentPages[position].fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentPages[position].title
    }

    override fun getItemPosition(fragment: Any): Int {
        for((index, page) in fragmentPages.withIndex()) {
            if (page.fragment == fragment) {
                return index
            }
        }
        return PagerAdapter.POSITION_NONE
    }

    override fun getCount() = fragmentPages.size

}