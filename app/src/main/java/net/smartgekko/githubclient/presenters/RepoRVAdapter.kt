package net.smartgekko.githubclient.presenters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import net.smartgekko.githubclient.databinding.ItemUserRepoBinding
import net.smartgekko.githubclient.repo.GitHubUserRepository
import net.smartgekko.githubclient.ui.RepoItemView
import java.util.*

class RepoRVAdapter(private val presenter: IUserReposListPresenter) :
    RecyclerView.Adapter<RepoRVAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemUserRepoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener { presenter.itemClickListener?.invoke(this) }
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        presenter.bindView(holder.apply { pos = position })

    override fun getItemCount() = presenter.getCount()

    inner class ViewHolder(private val vb: ItemUserRepoBinding) : RecyclerView.ViewHolder(vb.root),
        RepoItemView {
        override var pos = -1

        override fun setName(text: String) = with(vb) {
            repoNameTV.text = text
        }

        override fun setDesc(text: String) = with(vb) {
            repoDescTV.text = text
        }

        override fun textToggle() {
            if(vb.repoDescTV.isVisible) {
                vb.repoDescTV.visibility = View.GONE
            } else {
                vb.repoDescTV.visibility = View.VISIBLE
            }
        }
    }


}