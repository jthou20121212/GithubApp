package com.jthou.github.view.common

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.jthou.common.otherwise
import com.jthou.common.yes
import com.jthou.github.R
import com.jthou.github.model.account.AccountManager
import com.jthou.github.network.entites.User
import com.jthou.github.view.MainActivity
import com.jthou.github.view.config.ViewPagerFragmentConfig
import org.jetbrains.anko.support.v4.viewPager
import splitties.views.dsl.core.Ui
import splitties.views.dsl.core.verticalLayout

abstract class CommonViewPagerFragment : Fragment(), ViewPagerFragmentConfig,
   AccountManager.OnAccountStateChangeListener {

    val viewPageAdapter by lazy {
        CommonViewPageAdapter(childFragmentManager)
    }

    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AccountManager.onAccountStateChangeListeners.add(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        AccountManager.onAccountStateChangeListeners.remove(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return object : Ui {
            override val ctx: Context = container?.context ?: activity!!
            override val root = verticalLayout {
                viewPager = viewPager {
                    id = R.id.viewPager
                }
                viewPager.adapter = viewPageAdapter
            }
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).actionBarController.setupWithViewPager(viewPager)
        viewPageAdapter.fragmentPages.addAll(
            AccountManager
                .isLoggedIn()
                .yes {
                    getFragmentPagesLoggedIn()
                }.otherwise {
                    getFragmentPagesNotLoggedIn()
                }
        )
    }

    override fun onLogin(user: User) {
        viewPageAdapter.fragmentPages.clear()
        viewPageAdapter.fragmentPages.addAll(getFragmentPagesLoggedIn())
    }

    override fun onLogout() {
        viewPageAdapter.fragmentPages.clear()
        viewPageAdapter.fragmentPages.addAll(getFragmentPagesNotLoggedIn())
    }

}