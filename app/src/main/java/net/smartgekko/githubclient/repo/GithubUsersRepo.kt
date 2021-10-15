package net.smartgekko.githubclient.repo

import io.reactivex.Observable

interface GithubUsersRepo {
   // fun addUser(user: GithubUser)
    fun getUsersList(): Observable<ArrayList<GithubUser>>
}