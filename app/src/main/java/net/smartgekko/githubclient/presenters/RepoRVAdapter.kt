package net.smartgekko.githubclient.presenters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.smartgekko.githubclient.App
import net.smartgekko.githubclient.R
import net.smartgekko.githubclient.databinding.ItemUserRepoBinding
import net.smartgekko.githubclient.ui.RepoItemView

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
        presenter.bindView(holder.apply {
            pos = position
        }, holder.layoutPosition)

    override fun getItemCount() = presenter.getCount()

    inner class ViewHolder(private val vb: ItemUserRepoBinding) : RecyclerView.ViewHolder(vb.root),
        RepoItemView {
        override var pos = -1

        override fun setName(text: String) {
            vb.repoNameTV.text = text
        }

        override fun setDesc(text: String) {
            if (text != "") {
                vb.repoDescTV.text = text
            } else {
                vb.repoDescTV.text = App.instance.getString(R.string.no_description)
            }
        }

        override fun showDesc() {
            vb.repoDescTV.visibility = View.VISIBLE
        }

        override fun hideDesc() {
            vb.repoDescTV.visibility = View.GONE
        }

        override fun noteItemChanged(curPos: Int) {
            notifyDataSetChanged()
        }
    }
}