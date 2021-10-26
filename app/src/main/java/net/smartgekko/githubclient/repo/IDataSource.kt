package net.smartgekko.githubclient.repo

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path


interface IDataSource {
        @GET("/users")
        fun getUsers(): Observable<List<GithubUser>>
    }
