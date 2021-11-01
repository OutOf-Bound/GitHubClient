package net.smartgekko.githubclient.repo.api

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import net.smartgekko.githubclient.SCREEN_STATE_IDLE
import net.smartgekko.githubclient.classes.GithubUser
import net.smartgekko.githubclient.repo.api.IDataSource
import net.smartgekko.githubclient.repo.api.IGithubUsersRepo
import net.smartgekko.githubclient.repo.database.CacheDatabase
import net.smartgekko.githubclient.repo.network.INetworkStatus

class RetrofitGithubUsersRepo(val api: IDataSource, val networkStatus: INetworkStatus, val db: CacheDatabase) : IGithubUsersRepo {


    override fun getUsers() = networkStatus.isOnlineSingle().flatMap{ isOnline ->
            if(isOnline){
                Single.fromObservable(api.getUsers())

            } else {
               Single.fromObservable(Observable.fromArray(db.userDao.getAll()))
            }
        }.subscribeOn(Schedulers.io())

    override fun getUserRepository(userId: String, url: String) = networkStatus.isOnlineSingle().flatMap{ isOnline ->
        if(isOnline){
            Single.fromObservable(api.getUserRepository(url))
        } else {
            Single.fromObservable(Observable.fromArray(db.repositoryDao.findForUser(userId)))
        }
    }.subscribeOn(Schedulers.io())
}