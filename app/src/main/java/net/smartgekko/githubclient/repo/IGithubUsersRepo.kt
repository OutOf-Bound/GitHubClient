package net.smartgekko.githubclient.repo

import io.reactivex.Observable
import net.smartgekko.githubclient.classes.GitHubUserRepository
import net.smartgekko.githubclient.classes.GithubUser

interface IGithubUsersRepo {
    fun getUsers(): Observable<List<GithubUser>>
    fun getUserRepository(url: String): Observable<List<GitHubUserRepository>>
}