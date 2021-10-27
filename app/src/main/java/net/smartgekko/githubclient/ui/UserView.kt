package net.smartgekko.githubclient.ui

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import net.smartgekko.githubclient.repo.GitHubUserRepository

@StateStrategyType(AddToEndSingleStrategy::class)
interface UserView : MvpView {
    fun init()
    fun updateUserReposList()
}