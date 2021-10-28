package net.smartgekko.githubclient.ui.user

import net.smartgekko.githubclient.ui.IItemView

interface RepoItemView : IItemView {
    fun setName(text: String)
    fun setDesc(text: String)
    fun showDesc()
    fun hideDesc()
    fun noteItemChanged(curPos: Int)
}