package com.jthou.github.view.fragments.child

import android.os.Bundle
import com.jthou.github.network.entites.Repository
import com.jthou.github.network.entites.User
import com.jthou.github.presenter.RepoListPresenter
import com.jthou.github.settings.Key
import com.jthou.github.view.adapter.RepoListAdapter
import com.jthou.github.view.common.CommonListFragment

class RepoListFragment : CommonListFragment<Repository, RepoListPresenter>() {

    var user: User? = null

    override val mAdapter = RepoListAdapter()

    companion object {
        fun newInstance(user: User? = null) : RepoListFragment {
            val bundle = Bundle()
            bundle.putParcelable(Key.ARG_USER, user)
            val fragment = RepoListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = arguments?.getParcelable(Key.ARG_USER)
    }

}