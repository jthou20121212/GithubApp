package com.jthou.github.view.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter
import com.jthou.github.R
import com.jthou.github.model.page.ListPage
import com.jthou.github.view.widget.ErrorInfoView
import com.jthou.mvp.BaseFragment
import kotlinx.android.synthetic.main.list.*
import retrofit2.adapter.rxjava.GitHubPaging
import splitties.toast.toast
import splitties.views.onClick

abstract class CommonListFragment<DataType, out Presenter: CommonListPresenter<DataType, CommonListFragment<DataType, Presenter>>>: BaseFragment<Presenter>(){

    protected abstract val mAdapter: CommonListAdapter<DataType>

    protected val errorInfoView by lazy {
        ErrorInfoView(rootView)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshView.apply {
            isRefreshing = true
            setOnRefreshListener(presenter::refreshData)
            setColorSchemeResources(R.color.google_red, R.color.google_yellow, R.color.google_green, R.color.google_blue)
        }

        recyclerView.apply {
            adapter = LuRecyclerViewAdapter(mAdapter)
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            setLoadMoreEnabled(true)
            setOnLoadMoreListener(presenter::loadMore)
        }

        presenter.initData()
    }

    fun setLoadMoreEnable(isEnabled: Boolean){
        recyclerView.setLoadMoreEnabled(isEnabled)
    }

    fun onDataInit(data: GitHubPaging<DataType>) {
        mAdapter.data.addAll(data)
        refreshView.isRefreshing = false
        recyclerView.setNoMore(data.isLast)
        recyclerView.refreshComplete(ListPage.PAGE_SIZE)
        dismissError()
    }

    fun onDataRefresh(data: GitHubPaging<DataType>){
        mAdapter.data.clear()
        onDataInit(data)
    }

    fun onDataInitWithNothing() {
        showError("No Data.")
        recyclerView.setNoMore(true)
        recyclerView.refreshComplete(ListPage.PAGE_SIZE)
        refreshView.isRefreshing = false
        errorInfoView.isClickable = false
    }

    fun onDataInitWithError(error: String){
        showError(error)
        errorInfoView.onClick {
            presenter.initData()
        }
    }

    fun onDataRefreshWithError(error: String){
        if (mAdapter.data.isEmpty()){
            showError(error)
            errorInfoView.onClick {
                presenter.initData()
            }
        } else {
            toast(error)
        }
    }

    fun onMoreDataLoaded(data: GitHubPaging<DataType>){
        mAdapter.data.update(data)
        recyclerView.refreshComplete(ListPage.PAGE_SIZE)
        recyclerView.setNoMore(data.isLast)
        dismissError()
    }

    fun onMoreDataLoadedWithError(error: String){
        showError(error)
        recyclerView.refreshComplete(ListPage.PAGE_SIZE)
        errorInfoView.onClick {
            presenter.initData()
        }
    }

    fun showError(error: String) {
        errorInfoView.show(error)
    }

    fun dismissError() {
        errorInfoView.dismiss()
    }










}