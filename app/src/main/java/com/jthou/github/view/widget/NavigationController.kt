package com.jthou.github.view.widget

import android.view.MenuItem
import com.google.android.material.navigation.NavigationView
import com.jthou.github.R
import com.jthou.github.model.account.AccountManager
import com.jthou.github.settings.Settings
import com.jthou.github.utils.doOnLayoutAvailable
import com.jthou.github.utils.loadWithGlide
import com.jthou.github.utils.selectItem
import com.jthou.github.view.config.NavViewItem
import kotlinx.android.synthetic.main.nav_header_main.view.*
import splitties.views.imageResource
import splitties.views.onClick

class NavigationController(
    private val navigationView: NavigationView,
    private val onNavItemChanged: (NavViewItem) -> Unit,
    private val onHeaderClick: () -> Unit
) : NavigationView.OnNavigationItemSelectedListener {

    init {
        navigationView.setNavigationItemSelectedListener(this)
    }

    private var currentItem: NavViewItem? = null

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navigationView.apply {
            Settings.lastPage = item.itemId
            val navItem = NavViewItem[item.itemId]
            onNavItemChanged(navItem)
        }
        return true
    }

    fun useLoginLayout() {
        navigationView.menu.clear()
        navigationView.inflateMenu(R.menu.activity_main_drawer)
        onUpdate()
        selectProperItem()
    }

    fun useNoLoginLayout() {
        navigationView.menu.clear()
        navigationView.inflateMenu(R.menu.activity_main_drawer_no_logged_in) //inflate new items.
        onUpdate()
        selectProperItem()
    }

    private fun onUpdate() {
        navigationView.doOnLayoutAvailable {
            val user = AccountManager.currentUser
            navigationView.apply {
                usernameView.text = user?.login
                emailView.text = user?.email
                if (user == null) {
                    avatarView.imageResource = R.drawable.ic_github
                } else {
                    avatarView.loadWithGlide(user.avatar_url, user.login.first())
                }
                navigationHeader.onClick { onHeaderClick() }
            }
        }
    }

    fun selectProperItem() {
        navigationView.doOnLayoutAvailable {
            ((currentItem?.let { NavViewItem[it] } ?: Settings.lastPage)
                .takeIf { navigationView.menu.findItem(it) != null }
                ?: run { Settings.defaultPage })
                .let(navigationView::selectItem)
        }
    }

}