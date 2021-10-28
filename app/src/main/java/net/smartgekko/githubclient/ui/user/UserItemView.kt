package net.smartgekko.githubclient.ui.user

import net.smartgekko.githubclient.ui.IItemView

interface UserItemView : IItemView {
    fun setLogin(text: String)
}