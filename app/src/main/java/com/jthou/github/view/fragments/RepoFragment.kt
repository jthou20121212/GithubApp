package com.jthou.github.view.fragments

import com.jthou.github.model.account.AccountManager
import com.jthou.github.view.common.CommonViewPagerFragment
import com.jthou.github.view.config.FragmentPage
import com.jthou.github.view.fragments.child.RepoListFragment

class RepoFragment : CommonViewPagerFragment() {

    override fun getFragmentPagesLoggedIn() = listOf(
        FragmentPage(RepoListFragment.newInstance(AccountManager.currentUser), "My"),
        FragmentPage(RepoListFragment.newInstance(), "All")
    )

    override fun getFragmentPagesNotLoggedIn() =
        listOf(FragmentPage(RepoListFragment.newInstance(), "All"))

}