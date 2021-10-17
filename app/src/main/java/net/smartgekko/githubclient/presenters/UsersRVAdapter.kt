package net.smartgekko.githubclient.presenters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.smartgekko.githubclient.R
import net.smartgekko.githubclient.databinding.ItemUserBinding
import net.smartgekko.githubclient.ui.UserItemView


class UsersRVAdapter(private val presenter: IUserListPresenter) :
    RecyclerView.Adapter<UsersRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener { presenter.itemClickListener?.invoke(this) }
        }

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        presenter.bindView(holder.apply { pos = position })

    inner class ViewHolder(private val vb: ItemUserBinding) : RecyclerView.ViewHolder(vb.root),
        UserItemView {
        override var pos = -1
        override fun setLogin(text: String) = with(vb) {
            userListNameTV.text = text
        }

        override fun setUserState(state: Int) = with(vb) {
            when (state) {
                0 -> {
                    userSmileIV.setImageResource(R.drawable.smile_smile_300)
                }
                1 -> {
                    userSmileIV.setImageResource(R.drawable.smile_pockerface_300)
                }
                2 -> {
                    userSmileIV.setImageResource(R.drawable.smile_angry_300)
                }
                3 -> {
                    userSmileIV.setImageResource(R.drawable.smile_war_300)
                }
            }
        }
    }
}

