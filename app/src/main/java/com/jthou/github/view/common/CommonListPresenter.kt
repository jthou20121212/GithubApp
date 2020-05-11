package com.jthou.github.view.common

import com.jthou.github.model.page.ListPage
import com.jthou.mvp.BasePresenter
import rx.Subscription

abstract class CommonListPresenter<DataType, out View : CommonListFragment<DataType, CommonListPresenter<DataType, View>>> :
    BasePresenter<View>() {

    abstract val listPage: ListPage<DataType>

    private var isFirstInView = false
    private val subscriptionList = ArrayList<Subscription>()

    fun initData() {
        listPage
            .loadFromFirst()
            .subscribe({
                if (it.isEmpty()) {
                    view.onDataInitWithNothing()
                } else {
                    view.onDataInit(it)
                }
            }, {
                view.onDataInitWithError(it.message ?: it.toString())
            }).let(subscriptionList::add)
    }

    fun refreshData() {
        listPage
            .loadFromFirst()
            .subscribe({
                if (it.isEmpty()) {
                    view.onDataInitWithNothing()
                } else {
                    view.onDataRefresh(it)
                }
            }, {
                view.onDataRefreshWithError(it.message?:it.toString())
            })
            .let(subscriptionList::add)
    }

    fun loadMore() {
        listPage
            .loadMore()
            .subscribe({view.onMoreDataLoaded(it)}, { view.onMoreDataLoadedWithError(it.message?:it.toString()) })
            .let(subscriptionList::add)
    }

    override fun onResume() {
        super.onResume()
        if (!isFirstInView) {
            refreshData()
        }
        isFirstInView = false
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptionList.forEach { it.unsubscribe() }
        subscriptionList.clear()
    }

}