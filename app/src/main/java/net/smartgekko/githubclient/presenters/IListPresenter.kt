package net.smartgekko.githubclient.presenters

import net.smartgekko.githubclient.ui.IItemView
import net.smartgekko.githubclient.ui.UserItemView

interface IListPresenter<V : IItemView> {
    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}

interface IUserListPresenter : IListPresenter<UserItemView>