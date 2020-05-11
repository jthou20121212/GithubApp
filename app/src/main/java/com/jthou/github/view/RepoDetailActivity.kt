package com.jthou.github.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.jthou.github.R
import com.jthou.github.network.entites.Repository
import com.jthou.github.network.services.ActivityService
import com.jthou.github.network.services.RepositoryService
import com.jthou.github.settings.Key
import com.jthou.github.settings.Themer
import com.jthou.github.utils.*
import kotlinx.android.synthetic.main.activity_repo_details.*
import kotlinx.android.synthetic.main.app_bar_details.*
import retrofit2.HttpException
import retrofit2.Response
import rx.Subscriber

class RepoDetailActivity : BaseDetailSwipeFinishableActivity() {

    private lateinit var repository: Repository

    companion object {
        fun launchActivity(context: Context, repository: Repository) {
            val intent = Intent(context, RepoDetailActivity::class.java)
            intent.putExtra(Key.ARG_REPO, repository)
            context.startActivity(intent)
            if (context !is Activity) {
                return
            }
            context.overridePendingTransition(0, R.anim.left_out)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_details)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        repository = intent?.getParcelableExtra(Key.ARG_REPO)!!

        initDetails()
        reloadDetails()
    }

    private fun initDetails() {
        avatarView.loadWithGlide(repository.owner.avatar_url, repository.owner.login.first())
        collapsingToolbar.title = repository.name

        descriptionView.markdownText = getString(
            R.string.repo_description_template,
            repository.owner.login,
            repository.owner.html_url,
            repository.name,
            repository.html_url,
            repository.owner.login,
            repository.owner.html_url,
            githubTimeToDate(repository.created_at)?.view() ?: ""
        )
        bodyView.text = repository.description

        detailContainer.alpha = 0f

        val owner = repository.owner.login
        val name = repository.name

        stars.checkEvent = {isChecked ->
            if (isChecked) {
                ActivityService
                    .unstar(owner, name)
                    .map { false }
            } else {
                ActivityService.star(owner, name)
                    .map { true }
            }.doOnNext { reloadDetails(true) }
        }

        watches.checkEvent = { isChecked ->
            if(isChecked){
                ActivityService.unwatch(owner, name)
                    .map { false }
            } else {
                ActivityService.watch(owner, name)
                    .map { true }
            }.doOnNext { reloadDetails(true) }
        }

        ActivityService.isStarred(owner, name)
            .onErrorReturn {
                if (it is HttpException) {
                    it.response() as Response<Any>
                } else{
                    throw it
                }
            }
            .subscribeIgnoreError {
                stars.isChecked = it.isSuccessful
            }

        ActivityService.isWatched(repository.owner.login, repository.name)
            .subscribeIgnoreError {
                watches.isChecked = it.subscribed
            }












    }

    private fun reloadDetails(forceNetwork: Boolean = false) {
        RepositoryService
            .getRepository(repository.owner.login, repository.name, forceNetwork)
            .subscribe(object : Subscriber<Repository>() {
                override fun onStart() {
                    super.onStart()
                    loadingView.animate().alpha(1f).start()
                }

                override fun onNext(t: Repository) {
                    repository = t

                    owner.content = repository.owner.login
                    stars.content = repository.stargazers_count.toString()
                    watches.content = repository.subscribers_count.toString()
                    forks.content = repository.forks_count.toString()
                    issues.content = repository.open_issues_count.toString()

                    loadingView.animate().alpha(0f).start()
                    detailContainer.animate().alpha(1f).start()
                }

                override fun onCompleted() = Unit

                override fun onError(e: Throwable) {
                    loadingView.animate().alpha(0f).start()
                    e.printStackTrace()
                }
            })




















    }

}