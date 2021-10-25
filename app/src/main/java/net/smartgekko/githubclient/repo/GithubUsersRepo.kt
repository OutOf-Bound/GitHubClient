package net.smartgekko.githubclient.repo

import io.reactivex.Observable

interface GithubUsersRepo {
    fun getUsersList(): Observable<ArrayList<GithubUser>>
}