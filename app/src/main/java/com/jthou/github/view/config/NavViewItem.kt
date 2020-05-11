package com.jthou.github.view.config

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.jthou.github.R.drawable
import com.jthou.github.R.id
import com.jthou.github.view.fragments.AboutFragment
import com.jthou.github.view.fragments.MyIssueFragment
import com.jthou.github.view.fragments.PeopleFragment
import com.jthou.github.view.fragments.RepoFragment

class NavViewItem private constructor(val groupId: Int = 0, val title: String, @DrawableRes val icon: Int, val fragmentClass: Class<out Fragment>, val arguements: Bundle = Bundle()){

    companion object{
        private val items = mapOf(
                id.navRepos to NavViewItem(0, "Repository", drawable.ic_repository, RepoFragment::class.java),
                id.navPeople to NavViewItem(0, "People", drawable.ic_people, PeopleFragment::class.java),
                id.navIssue to NavViewItem(0, "Issue", drawable.ic_issue, MyIssueFragment::class.java),
                id.navAbout to NavViewItem(0, "About", drawable.ic_about_us, AboutFragment::class.java)
        )

        operator fun get(@IdRes navId: Int): NavViewItem {
            return items[navId]?: items[id.navRepos]!!
        }

        operator fun get(item: NavViewItem): Int{
            return items.filter { it.value == item }.keys.first()
        }
    }

    override fun toString(): String {
        return "NavViewItem(groupId=$groupId, title='$title', icon=$icon, fragmentClass=$fragmentClass, arguements=$arguements)"
    }

}
