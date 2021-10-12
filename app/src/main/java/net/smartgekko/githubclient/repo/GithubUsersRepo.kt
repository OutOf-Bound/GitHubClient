package net.smartgekko.githubclient.repo

import io.reactivex.Observable

interface GithubUsersRepo {
    fun add(user: GithubUser)
    val users: Observable<ArrayList<GithubUser>>
}