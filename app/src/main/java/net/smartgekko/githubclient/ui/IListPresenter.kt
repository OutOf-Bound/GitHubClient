package net.smartgekko.githubclient.presenters

import net.smartgekko.githubclient.ui.IItemView
import net.smartgekko.githubclient.ui.user.RepoItemView
import net.smartgekko.githubclient.ui.user.UserItemView

interface IListPresenter<V : IItemView> {
    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V, lPos: Int)
    fun getCount(): Int
}

interface IUserListPresenter : IListPresenter<UserItemView>
interface IUserReposListPresenter : IListPresenter<RepoItemView>