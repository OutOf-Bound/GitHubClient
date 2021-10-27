package net.smartgekko.githubclient.repo

import io.reactivex.schedulers.Schedulers

class RetrofitGithubUsersRepo(val api: IDataSource): IGithubUsersRepo {
    override fun getUsers() = api.getUsers().subscribeOn(Schedulers.io())
    override fun getUserRepository(url:String) = api.getUserRepository(url).subscribeOn(Schedulers.io())
}