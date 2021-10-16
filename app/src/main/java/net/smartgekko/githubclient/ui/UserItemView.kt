package net.smartgekko.githubclient.ui

interface UserItemView : IItemView {
    fun setLogin(text: String)
    fun setUserState(state:Int)

}