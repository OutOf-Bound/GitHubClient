package net.smartgekko.githubclient.ui

import net.smartgekko.githubclient.App
import net.smartgekko.githubclient.repo.api.ApiHolder
import net.smartgekko.githubclient.repo.api.IGithubUsersRepo
import net.smartgekko.githubclient.repo.api.RetrofitGithubUsersRepo

interface GithubPresenter {
    val mainRepo:IGithubUsersRepo
    get() =  RetrofitGithubUsersRepo(ApiHolder.apiRetrofit, App.networkStatus)
}