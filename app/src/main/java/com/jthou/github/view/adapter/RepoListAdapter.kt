package com.jthou.github.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jthou.github.R
import com.jthou.github.network.entites.Repository
import com.jthou.github.utils.loadWithGlide
import com.jthou.github.utils.toKilo
import com.jthou.github.view.RepoDetailActivity
import com.jthou.github.view.common.CommonListAdapter
import kotlinx.android.synthetic.main.item_repo.view.*

class RepoListAdapter : CommonListAdapter<Repository>(R.layout.item_repo) {

    override fun onBindData(viewHolder: RecyclerView.ViewHolder, item: Repository) {
        viewHolder.itemView.apply {
            avatarView.loadWithGlide(item.owner.avatar_url, item.owner.login.first())
            repoNameView.text = item.name
            descriptionView.text = item.description
            langView.text = item.language ?: "Unknown"
            starView.text = item.stargazers_count.toKilo()
            forkView.text = item.forks_count.toKilo()
        }
    }

    override fun onItemClicked(itemView: View, item: Repository) {
        RepoDetailActivity.launchActivity(itemView.context, item)
    }

}