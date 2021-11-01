package net.smartgekko.githubclient.repo.api

import io.reactivex.Observable
import net.smartgekko.githubclient.classes.GitHubUserRepository
import net.smartgekko.githubclient.classes.GithubUser
import retrofit2.http.GET
import retrofit2.http.Url


interface IDataSource {
    @GET("/users")
    fun getUsers(): Observable<List<GithubUser>>

    @GET
    fun getUserRepository(@Url url: String): Observable<List<GitHubUserRepository>>
}