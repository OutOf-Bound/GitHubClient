package net.smartgekko.githubclient.repo.api

import io.reactivex.Single
import net.smartgekko.githubclient.classes.GithubUserRepository
import net.smartgekko.githubclient.classes.GithubUser
import net.smartgekko.githubclient.repo.cache.RoomGithubRepositoriesCache
import net.smartgekko.githubclient.repo.cache.RoomGithubUsersCache

interface IGithubUsersRepo {
    val cacheUsers: RoomGithubUsersCache
    get() = RoomGithubUsersCache()

    val cacheUsersRepositories: RoomGithubRepositoriesCache
    get() = RoomGithubRepositoriesCache()

    fun getUsers(): Single<List<GithubUser>>
    fun getUserRepositoriesList(userId: String, url: String): Single<List<GithubUserRepository>>
}