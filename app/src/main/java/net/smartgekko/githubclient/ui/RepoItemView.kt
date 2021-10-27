package net.smartgekko.githubclient.ui

interface RepoItemView : IItemView {
    fun setName(text: String)
    fun setDesc(text: String)
    fun showDesc()
    fun hideDesc()
    fun noteItemChanged(curPos: Int)
}