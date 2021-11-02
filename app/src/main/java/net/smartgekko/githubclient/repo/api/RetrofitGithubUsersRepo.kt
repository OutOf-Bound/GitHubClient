package net.smartgekko.githubclient.repo.api

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.smartgekko.githubclient.App
import net.smartgekko.githubclient.repo.cache.database.CacheDatabase
import net.smartgekko.githubclient.repo.network.INetworkStatus

class RetrofitGithubUsersRepo(
    val api: IDataSource,
    val networkStatus: INetworkStatus,
    val db: CacheDatabase
) : IGithubUsersRepo {



    override fun getUsers() = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            Single.fromObservable(api.getUsers()).doOnSuccess {
                db.userDao.insert(it)
            }

        } else {
            Single.fromObservable(Observable.fromArray(db.userDao.getAll())).doAfterSuccess {
                App.instance.showMessage("No network. Data from cache")
            }.subscribeOn(AndroidSchedulers.mainThread())

        }
    }.subscribeOn(Schedulers.io())

    override fun getUserRepositoriesList(userId: String, url: String) =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                Single.fromObservable(api.getUserRepository(url)).doOnSuccess {
                    it.forEach {
                        it.userId = userId
                    }
                    db.repositoryDao.insert(it)
                }
            } else {
                Single.fromObservable(Observable.fromArray(db.repositoryDao.findForUser(userId))).doAfterSuccess {
                    App.instance.showMessage("No network. Data from cache")
                }.subscribeOn(AndroidSchedulers.mainThread())
            }
        }.subscribeOn(Schedulers.io())
}