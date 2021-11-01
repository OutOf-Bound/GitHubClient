package net.smartgekko.githubclient.repo.api

import io.reactivex.Observable
import io.reactivex.Single
import net.smartgekko.githubclient.classes.GitHubUserRepository
import net.smartgekko.githubclient.classes.GithubUser

interface IGithubUsersRepo {
    fun getUsers(): Single<List<GithubUser>>
    fun getUserRepository(userId: String, url: String): Single<List<GitHubUserRepository>>
}