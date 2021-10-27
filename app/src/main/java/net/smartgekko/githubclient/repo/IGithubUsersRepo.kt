package net.smartgekko.githubclient.repo

import io.reactivex.Observable

interface IGithubUsersRepo {
    fun getUsers(): Observable<List<GithubUser>>
    fun getUserRepository(url: String): Observable<List<GitHubUserRepository>>
}