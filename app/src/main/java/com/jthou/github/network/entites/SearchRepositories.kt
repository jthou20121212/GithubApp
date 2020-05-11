package com.jthou.github.network.entites

import com.jthou.common.anko.PoKo
import retrofit2.adapter.rxjava.PagingWrapper

@PoKo
data class SearchRepositories(var total_count: Int,
                              var incomplete_results: Boolean,
                              var items: List<Repository>) : PagingWrapper<Repository>() {

    override fun getElements() = items

}