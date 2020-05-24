package com.jthou.github.view

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.jthou.common.no
import com.jthou.common.otherwise
import com.jthou.common.yes
import com.jthou.github.R
import com.jthou.github.model.account.AccountManager
import com.jthou.github.network.entites.User
import com.jthou.github.settings.Themer
import com.jthou.github.utils.*
import com.jthou.github.view.config.NavViewItem
import com.jthou.github.view.widget.ActionBarController
import com.jthou.github.view.widget.NavigationController
import com.jthou.github.view.widget.confirm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.menu_item_daynight.view.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.coroutines.GlobalScope
import splitties.activities.start
import splitties.toast.toast

class MainActivity : AppCompatActivity(), AccountManager.OnAccountStateChangeListener {

    private lateinit var toggle: ActionBarDrawerToggle

    val actionBarController by lazy {
        ActionBarController(this)
    }

    private val navigationController by lazy {
        NavigationController(navigationView, ::onNavItemChanged, ::handleNavigationHeaderClickEvent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Themer.applyProperTheme(this)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        initNavigationView()

        AccountManager.onAccountStateChangeListeners.add(this)

//        RepositoryService
//            .allRepositories(2, "<pushed:<" + Date().format("yyyy-MM-dd"))
//            .subscribe({
//                logger.debug("result : {$it.toString()}")
//            }, { it.printStackTrace() })
    }

    override fun onDestroy() {
        AccountManager.onAccountStateChangeListeners.remove(this)
        drawer_layout?.removeDrawerListener(toggle)
        super.onDestroy()
    }

    private fun initNavigationView() {
        AccountManager
            .isLoggedIn()
            .yes {
                navigationController.useLoginLayout()
            }
            .otherwise {
                navigationController.useNoLoginLayout()
            }
    }

    private fun handleNavigationHeaderClickEvent() {
        AccountManager
            .isLoggedIn()
            .no {
                start<LoginActivity>()
            }.otherwise {
                GlobalScope.launchMain {
                    confirm(getString(R.string.logout_title), getString(R.string.logout_message)).yes {
                        AccountManager
                            .logout()
                            .subscribe(
                                {
                                    toast(R.string.logout_success)
                                },
                                {
                                    toast(R.string.logout_failure)
                                    it.printStackTrace()
                                }
                            )
                    }
                }
            }
    }

    private fun updateNavigationView(user: User) {
        navigationView.doOnLayoutAvailable {
            usernameView.text = user.login
            emailView.text = user.email
            avatarView.loadWithGlide(user.avatar_url, user.login.first())
        }
    }

    private fun clearNavigationView() {
        navigationView.doOnLayoutAvailable {
            usernameView.text = "请登录"
            emailView.text = null
            avatarView.setImageResource(R.drawable.ic_github)
        }
    }

    private fun onNavItemChanged(navViewItem: NavViewItem) {
        drawer_layout.afterClose {
            showFragment(R.id.fragmentContainer, navViewItem.fragmentClass, navViewItem.arguements)
            title = navViewItem.title
        }
    }

    override fun onLogin(user: User) {
        updateNavigationView(user)
    }

    override fun onLogout() {
        clearNavigationView()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_actionbar, menu)
        menu.findItem(R.id.dayNight).actionView.dayNightSwitch.apply {
            isChecked = Themer.currentTheme() == Themer.ThemeMode.DAY
            setOnCheckedChangeListener { _, _ ->
                Themer.toggle(this@MainActivity)
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

}
