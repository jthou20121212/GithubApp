package com.jthou.github.view.widget

import android.database.DataSetObserver
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.jthou.github.view.MainActivity
import kotlinx.android.synthetic.main.app_bar_main.*

class ActionBarController(private val mainActivity: MainActivity) {

    private val tabLayout by lazy {
        mainActivity.tabLayout
    }

    class ViewPagerDataSetObserver(private val tabLayout: TabLayout) : DataSetObserver() {

        var viewPager: ViewPager? = null
            set(value) {
                viewPager?.adapter?.unregisterDataSetObserver(this)
                value?.adapter?.registerDataSetObserver(this)
                field = value
            }

        override fun onChanged() {
            super.onChanged()
            viewPager?.let {
                if (it.adapter?.count ?: 0 <= 1) {
                    tabLayout.visibility = View.GONE
                } else {
                    tabLayout.visibility = View.VISIBLE
                    tabLayout.tabMode =
                        if (it.adapter?.count ?: 0 > 3) TabLayout.MODE_SCROLLABLE else TabLayout.MODE_FIXED
                }
            }
        }

    }

    private val dataSetObserver by lazy {
        ViewPagerDataSetObserver(tabLayout)
    }

    public fun setupWithViewPager(viewPager: ViewPager?) {
        viewPager?.let(dataSetObserver::viewPager::set) ?: run { tabLayout.visibility = View.GONE }
        tabLayout.setupWithViewPager(viewPager)
    }


}