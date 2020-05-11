package com.jthou.github.presenter

import com.jthou.github.model.repo.RepoListPage
import com.jthou.github.network.entites.Repository
import com.jthou.github.view.common.CommonListPresenter
import com.jthou.github.view.fragments.child.RepoListFragment

class RepoListPresenter : CommonListPresenter<Repository, RepoListFragment>() {

    override val listPage by lazy {
        RepoListPage(view.user)
    }

}