package net.smartgekko.githubclient.ui

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface UserView : MvpView {
    fun setScreenState(state: Int)
    fun init()
    fun updateUserReposList()
}