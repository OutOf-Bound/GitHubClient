package net.smartgekko.githubclient.repo

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url


interface IDataSource {
    @GET("/users")
    fun getUsers(): Observable<List<GithubUser>>

    @GET
    fun getUserRepository(@Url url: String): Observable<List<GitHubUserRepository>>
}