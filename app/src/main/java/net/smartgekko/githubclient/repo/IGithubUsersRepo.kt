package net.smartgekko.githubclient.repo

import io.reactivex.Observable
import io.reactivex.Single

interface IGithubUsersRepo {
    fun getUsers(): Observable<List<GithubUser>>
}