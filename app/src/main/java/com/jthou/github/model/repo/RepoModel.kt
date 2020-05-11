package com.jthou.github.model.repo

import com.jthou.github.model.page.ListPage
import com.jthou.github.network.entites.Repository
import com.jthou.github.network.entites.User
import com.jthou.github.network.services.RepositoryService
import com.jthou.github.utils.format
import retrofit2.adapter.rxjava.GitHubPaging
import rx.Observable
import java.util.*

class RepoListPage(private val owner: User?) : ListPage<Repository>() {

    override fun getData(page: Int): Observable<GitHubPaging<Repository>> {
       return if (owner == null) {
           RepositoryService.allRepositories(page, "<pushed:<" + Date().format("yyyy-MM-dd")).map { it.paging }
       } else{
           RepositoryService.listRepositoriesOfUser(owner.login, page)
       }
    }

}